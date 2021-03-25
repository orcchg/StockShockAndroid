package com.orcchg.yandexcontest.stocklist.data

import android.text.format.DateUtils.DAY_IN_MILLIS
import com.orcchg.yandexcontest.core.network.api.NetworkRetryFailedException
import com.orcchg.yandexcontest.core.network.api.handleHttpError
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.stocklist.api.model.Index
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.QuoteDao
import com.orcchg.yandexcontest.stocklist.data.local.StockListSharedPrefs
import com.orcchg.yandexcontest.stocklist.data.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.local.convert.QuoteDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.StockListRest
import com.orcchg.yandexcontest.stocklist.data.remote.StockListWebSocket
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IndexNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IssuerNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IssuerNetworkToDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.QuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import com.orcchg.yandexcontest.util.algorithm.InMemorySearchManager
import com.orcchg.yandexcontest.util.suppressErrors
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StockListRepositoryImpl @Inject constructor(
    private val restCloud: StockListRest,
    private val webSocketClout: StockListWebSocket,
    private val localIssuer: IssuerDao,
    private val localQuote: QuoteDao,
    private val indexNetworkConverter: IndexNetworkConverter,
    private val issuerLocalConverter: IssuerDboConverter,
    private val issuerNetworkConverter: IssuerNetworkConverter,
    private val issuerNetworkToLocalConverter: IssuerNetworkToDboConverter,
    private val quoteLocalConverter: QuoteDboConverter,
    private val quoteNetworkConverter: QuoteNetworkConverter,
    private val sharedPrefs: StockListSharedPrefs
) : StockListRepository {

    private val _favouriteIssuersChanged = PublishSubject.create<IssuerFavourite>()
    override val favouriteIssuersChanged: Observable<IssuerFavourite> = _favouriteIssuersChanged.hide()

    override fun defaultIssuers(): Single<List<Issuer>> =
        defaultNetworkIssuers().toObservable()
            .publish { network -> Observable.merge(network, defaultLocalIssuers().toObservable().takeUntil(network)) }
            .first(emptyList())

    override fun favouriteIssuers(): Single<List<Issuer>> =
        localIssuer.favouriteIssuers().map(issuerLocalConverter::convertList)

    override fun findIssuers(query: String): Single<List<Issuer>> =
        localIssuer.findIssuers("$query%").map(issuerLocalConverter::convertList)

    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        Completable.fromCallable {
            val partial = IssuerFavourite(ticker, isFavourite)
            localIssuer.setIssuerFavourite(partial)
            _favouriteIssuersChanged.onNext(partial)
        }

    override fun quote(ticker: String): Single<Quote> =
        quoteNetwork(ticker).toObservable()
            .publish { network -> Observable.merge(network, quoteLocal(ticker).takeUntil(network)) }
            .first(Quote() /* default quote */)

    override fun invalidateCache(stockSelection: StockSelection): Completable =
        Completable.fromCallable {
            when (stockSelection) {
                StockSelection.ALL, StockSelection.FAVOURITE -> sharedPrefs.recordDefaultIssuersCacheTimestamp(0L)
                else -> throw IllegalStateException("Unsupported stock selection")
            }
        }

    private fun quoteLocal(ticker: String): Observable<Quote> =
        localQuote.quote(ticker).map(quoteLocalConverter::convert).toObservable()

    private fun quoteNetwork(ticker: String): Single<Quote> =
        restCloud.quote(ticker)
            .handleHttpError(errorCode = 429, retryCount = 6) { error, index -> Timber.w(error, "'quote': retry from '$error', attempt: $index") }
            .onErrorResumeNext { error ->
                if (error is NetworkRetryFailedException) {
                    Timber.w("Failed to get quote for $ticker, skip")
                    Single.just(QuoteEntity()) // failed to get quote, use default instead
                } else {
                    Single.error(error)
                }
            }
            .map(quoteNetworkConverter::convert)
            .flatMap { quote ->
                Completable.fromCallable { localQuote.addQuote(quoteLocalConverter.revert(ticker, quote)) }
                    .toSingleDefault(quote)
            }

    private fun defaultLocalIssuers(): Maybe<List<Issuer>> =
        localIssuer.issuers()
            .filter(::isDefaultLocalIssuersUpToDate)
            .map(issuerLocalConverter::convertList)
            .doOnSuccess { issuers ->
                issuers.forEach { InMemorySearchManager.addWord(it.name) }
            }

    private fun defaultNetworkIssuers(): Single<List<Issuer>> =
        index()
            .flatMapObservable { index ->
                // limit by 30 requests per second to avoid HTTP 429 from Finnhub
                val chunks = index.tickers.chunked(30)
                Observable.fromIterable(chunks)
                    .zipWith(Observable.range(0, chunks.size)) { c, i -> c to i }
                    .flatMap { (c, i) -> Observable.just(c).delay(if (i > 0) 1000L else 0L, TimeUnit.MILLISECONDS) }
                    .concatMap { chunk ->
                        Timber.v("Issuers: ${chunk.joinToString(", ")}")
                        Observable.fromIterable(chunk)
                            .flatMapSingle(restCloud::issuer)
                            .handleHttpError(errorCode = 429, retryCount = 3) { error, index -> Timber.w(error, "'issuer' retry from '$error', attempt: $index") }
                            .suppressErrors { Timber.w("Skip issuer") }
                            .map(issuerNetworkToLocalConverter::convert)
                    }
            }
            .toList() // all issuers have been loaded from network
            .flatMap { issuers ->
                Single.fromCallable {
                    localIssuer.addIssuers(issuers) // cache loaded issuers
                    localQuote.clear() // invalidate all quotes, to be refreshed
                }
            }
            // cache is up to date now
            .doOnSuccess { sharedPrefs.recordDefaultIssuersCacheTimestamp(System.currentTimeMillis()) }
            .flatMapMaybe { defaultLocalIssuers() } // local cache is a single source of truth
            .toSingle(emptyList()) // just to cast result

    private inline fun <reified T> isDefaultLocalIssuersUpToDate(data: List<T>): Boolean =
        data.isNotEmpty() &&
            (System.currentTimeMillis() - sharedPrefs.getDefaultIssuersCacheTimestamp()) < DAY_IN_MILLIS

    private fun index() = restCloud.index(symbol = "^GSPC").map(indexNetworkConverter::convert)

    private fun popularIndex() =
        Single.just(Index(
            name = "POPULAR",
            tickers = listOf(
                "AAPL", "MRNA", "NFLX", "GOOGL", "TSLA", "B", "T", "FB", "MSFT", "AMZN",
                "WU", "BBY", "ZM", "PFE", "NKLA", "ATVI", "PTON", "GM", "UBER", "BYND",
                "GE", "DE", "BLK", "QCOM", "BIDU", "BABA", "DAL", "BA", "PYPL", "TWTR",
                "CAT", "NET", "CCL", "KO", "AA", "HAL", "ESS", "WMT"
            )
        ))
}
