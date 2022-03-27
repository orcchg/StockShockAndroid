package com.orcchg.yandexcontest.stocklist.data

import android.text.format.DateUtils.DAY_IN_MILLIS
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagManager
import com.orcchg.yandexcontest.core.network.api.NetworkRetryFailedException
import com.orcchg.yandexcontest.core.network.api.handleHttpError
import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Index
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.QuoteDao
import com.orcchg.yandexcontest.stocklist.data.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.local.convert.QuoteDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.StockListRest
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IndexNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IssuerNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IssuerNetworkToDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.QuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import com.orcchg.yandexcontest.util.algorithm.InMemorySearchManager
import com.orcchg.yandexcontest.util.suppressErrors
import com.orcchg.yandexcontest.util.toSet
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class StockListRepositoryImpl @Inject constructor(
    private val restCloud: StockListRest,
    private val localIssuer: IssuerDao,
    private val localQuote: QuoteDao,
    private val indexNetworkConverter: IndexNetworkConverter,
    private val issuerLocalConverter: IssuerDboConverter,
    private val issuerNetworkConverter: IssuerNetworkConverter,
    private val issuerNetworkToLocalConverter: IssuerNetworkToDboConverter,
    private val quoteLocalConverter: QuoteDboConverter,
    private val quoteNetworkConverter: QuoteNetworkConverter,
    private val featureFlagManager: FeatureFlagManager,
    private val schedulersFactory: SchedulersFactory
) : StockListRepository {

    private val _favouriteIssuersChanged = PublishSubject.create<IssuerFavourite>()
    override val favouriteIssuersChanged: Observable<IssuerFavourite> = _favouriteIssuersChanged.hide()

    /**
     * Holds a set of tickers for which it's failed to fetch [Quote]s from the network.
     * Invalidates each time a refresh is triggered. Such [Quote]s will be fetched in
     * a background and applied later on a client side.
     */
    private val missingQuoteTickers = mutableSetOf<String>()
    private val missingQuotesLoading = AtomicBoolean(false)

    /**
     * Holds a set of [Quote]s that had been missing before and then successfully fetched
     * from the network to be applied later on a client side.
     */
    private val _missingQuotesSource = PublishSubject.create<Collection<Quote>>()
    override val missingQuotes: Observable<Collection<Quote>> = _missingQuotesSource.hide()

    override fun issuer(ticker: String): Maybe<Issuer> =
        restCloud.issuer(ticker)
            .handleHttpError(errorCode = 429) { error, index -> Timber.w(error, "'issuer' ($ticker) retry from '$error', attempt: $index") }
            .map(issuerNetworkConverter::convert)
            .toObservable()
            .publish { network -> Observable.merge(network, localIssuer(ticker).toObservable().takeUntil(network)) }
            .firstOrError()
            .toMaybe()

    /**
     * Retrieves list of [Issuer] corresponding to a given [Index.name].
     *
     * [Index] is always fetched from network via single request. Local cache [IssuerDao]
     * is then checked for presence of each particular [Issuer.ticker] in [Index.tickers].
     * Only those [Issuer] which are missing in local cache will be fetched from network
     * and then added to the local cache.
     *
     * Eventually, [Issuer] items from local cache will be retrieved, as from a single
     * source of truth.
     */
    override fun defaultIssuers(): Single<List<Issuer>> =
        index() // load full index and retain only those issuers missing in local cache
            .doOnSuccess { Timber.v("Size of Index ${it.name}: ${it.tickers.size}") }
            .retainOnlyIssuersMissingInCache()
            .flatMapCompletable { index ->
                Timber.v("To be fetched: $index")
                if (index.tickers.isEmpty()) {
                    // either all issuers from index are already cached, or there is no issuers at all
                    Timber.v("Issuers' local cache is up to date")
                    Completable.complete() // get issuers from local cache
                } else {
                    // found new issuers in index that haven't been cached yet, fetch them from network
                    // limit by 'API_REQUEST_LIMIT' requests per second to avoid HTTP 429 from Finnhub
                    val chunks = index.tickers.chunked(API_REQUEST_LIMIT)
                    Timber.v("Start fetching ${index.tickers.size} issuers (${chunks.size} chunks)...")

                    Observable.fromIterable(chunks).take(2) // fetch 2 chunks at a time
                        .zipWith(Observable.range(0, chunks.size)) { c, i -> c to i }
                        .concatMap { (c, i) ->
                            Observable.just(c).delay(if (i > 0) 1000L else 0L, TimeUnit.MILLISECONDS, schedulersFactory.singleThread())
                        }
                        .concatMapCompletable { chunk ->
                            Timber.v("Issuers: ${chunk.joinToString(", ")}")
                            Observable.fromIterable(chunk)
                                .flatMapSingle(restCloud::issuer)
                                .handleHttpError(errorCode = 429) { error, index -> Timber.w(error, "'issuer' retry from '$error', attempt: $index") }
                                .suppressErrors { Timber.w(it, "Skip issuer") }
                                .map(issuerNetworkToLocalConverter::convert)
                                .toList() // chunk of issuers has been loaded
                                .flatMapCompletable { issuers -> // cache loaded issuers
                                    Completable.fromAction { localIssuer.addIssuers(issuers) }
                                }
                        }
                }
            }
            .toSingleDefault(0L)
            // cache is up to date now, get issuer from it as a single source of truth
            .flatMap { localIssuers() }
            .doOnSuccess { issuers -> // populate in-memory data structure to search issuers by name
                // tickers are not added in order to avoid them to appear in recent searches
                issuers.forEach { InMemorySearchManager.addWord(it.name) }
            }

    override fun favouriteIssuers(): Single<List<Issuer>> =
        localIssuer.favouriteIssuers().map(issuerLocalConverter::convertList)

    override fun localIssuer(ticker: String): Maybe<Issuer> =
        localIssuer.issuer(ticker).map(issuerLocalConverter::convert)

    override fun localIssuers(): Single<List<Issuer>> =
        localIssuer.issuers().map(issuerLocalConverter::convertList)

    override fun localFavouriteIssuers(): Single<List<Issuer>> = favouriteIssuers()

    override fun findIssuers(query: String): Single<List<Issuer>> =
        localIssuer.findIssuers("$query%").map(issuerLocalConverter::convertList)

    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        Completable.fromAction {
            val partial = IssuerFavourite(ticker, isFavourite)
            localIssuer.setIssuerFavourite(partial)
            _favouriteIssuersChanged.onNext(partial)
        }

    override fun quote(ticker: String): Single<Quote> =
        quoteNetwork(ticker).toObservable()
            // let network and local sources to compete which will emit faster and take the winner one
            .publish { network -> Observable.merge(network, quoteLocal(ticker).toObservable().takeUntil(network)) }
            .first(Quote(ticker)) // take one who emits first (either network or local)

    override fun emptyQuote(ticker: String): Single<Quote> =
        quoteLocal(ticker)
            .switchIfEmpty(
                Single.fromCallable {
                    missingQuoteTickers.add(ticker) // keep ticker to load it's quote later
                    Quote(ticker) // quickly respond with empty quote for ticker
                }
            )

    @Suppress("CheckResult")
    override fun getMissingQuotes(): Completable =
        Completable.fromAction {
            val progress = missingQuotesLoading.getAndSet(true)
            if (progress) {
                // missing quotes loading is already in progress from some another thread, skip
                // complete work now
            } else {
                var iterations = 0
                while (missingQuoteTickers.isNotEmpty()) {
                    Timber.v("Iteration [${iterations + 1}]: get missing quotes for ${missingQuoteTickers.size} tickers: ${missingQuoteTickers.joinToString()}")
                    val copy = mutableSetOf<String>().apply { addAll(missingQuoteTickers) }
                    val chunks = copy.chunked(API_REQUEST_LIMIT_SMALL)

                    Observable.fromIterable(chunks)
                        .doOnSubscribe { ++iterations }
                        .zipWith(Observable.range(0, chunks.size)) { c, i -> c to i }
                        .concatMap { (c, i) ->
                            Observable.just(c).delay(if (i > 0) 1000L else 0L, TimeUnit.MILLISECONDS, schedulersFactory.singleThread())
                        }
                        .concatMapSingle { chunk ->
                            Timber.v("Missing Quotes Chunk: ${chunk.joinToString(", ")}")
                            Observable.fromIterable(chunk)
                                .flatMapSingle { ticker -> quoteNetwork(ticker) }
                                .filter { !it.currentPrice.isZero() }
                                .toList()
                        }
                        .subscribe(_missingQuotesSource::onNext, Timber::e)

                    Timber.v("Finished iteration $iterations to get missing quotes")
                }
                Timber.v("Finished load missing quotes, iterations: $iterations")
                missingQuotesLoading.set(false) // finished
            }
        }

    override fun invalidateCache(): Completable =
        Completable.fromAction { missingQuoteTickers.clear() }

    private fun quoteLocal(ticker: String): Maybe<Quote> =
        localQuote.quote(ticker)
            // cached quote is considered stale if it has been cached more that a day ago
            .filter { System.currentTimeMillis() - it.timestamp < DAY_IN_MILLIS }
            .map(quoteLocalConverter::convert)

    private fun quoteNetwork(ticker: String): Single<Quote> =
        restCloud.quote(ticker)
            .handleHttpError(errorCode = 429) { error, index -> Timber.w(error, "'quote': retry from '$error', attempt: $index") }
            .doOnSuccess { missingQuoteTickers.remove(ticker) }
            .onErrorResumeNext { error ->
                if (error is NetworkRetryFailedException) {
                    Timber.w("Failed to get quote for $ticker, skip")
                    missingQuoteTickers.add(ticker) // failed to get quote for this ticker, keep it for later
                    Single.just(QuoteEntity()) // failed to get quote, use default one instead
                } else {
                    Single.error(error)
                }
            }
            .map { quoteNetworkConverter.convert(ticker, it) }
            .flatMap { quote ->
                // quote database object will be created from quote domain object
                // at the current timestamp, which will be used as an age of quote entry in cache
                Completable.fromAction { localQuote.addQuote(quoteLocalConverter.revert(quote)) }
                    .toSingleDefault(quote)
            }

    private fun index(): Single<Index> {
        val indexId: String? = when (featureFlagManager.getStockIndex()) {
            "S&P500" -> "^GSPC"
            "NASDAQ" -> "^NDX"
            "DOW" -> "^DJI"
            else -> null
        }

        return indexId?.let {
            restCloud.index(symbol = it).map(indexNetworkConverter::convert)
        }
            ?: popularIndex()
    }

    private fun Single<Index>.retainOnlyIssuersMissingInCache(): Single<Index> =
        flatMap { index ->
            Observable.fromIterable(index.tickers)
                .filter(localIssuer::noIssuer)
                .toSet()
                .map { tickers -> Index(tickers, name = index.name) }
        }

    @Suppress("Unused")
    private fun popularIndex() =
        Single.just(
            Index(
                name = POPULAR_INDEX,
                tickers = listOf(
                    "AAPL", "MRNA", "NFLX", "GOOGL", "TSLA", "B", "T", "FB", "MSFT", "AMZN",
                    "WU", "BBY", "ZM", "PFE", "NKLA", "ATVI", "PTON", "GM", "UBER", "BYND",
                    "GE", "DE", "BLK", "QCOM", "BIDU", "BABA", "DAL", "BA", "PYPL", "TWTR",
                    "CAT", "NET", "CCL", "KO", "AA", "HAL", "ESS", "WMT"
                )
            )
        )

    companion object {
        private const val POPULAR_INDEX = "POPULAR"
        private const val API_REQUEST_LIMIT = 30
        private const val API_REQUEST_LIMIT_SMALL = 3
    }
}
