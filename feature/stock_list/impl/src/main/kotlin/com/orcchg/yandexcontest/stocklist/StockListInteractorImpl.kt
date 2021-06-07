package com.orcchg.yandexcontest.stocklist

import android.annotation.SuppressLint
import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.domain.usecase.FavouriteIssuersChangedUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.FindIssuersByQueryUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetDefaultIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetEmptyQuoteByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetFavouriteIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetIssuerByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalFavouriteIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalIssuerByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetMissingQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetQuoteByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetRealTimeQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.InvalidateCacheUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.MissingQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.SetIssuerFavouriteUseCase
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@SuppressLint("CheckResult")
class StockListInteractorImpl @Inject constructor(
    private val findIssuersByQueryUseCase: FindIssuersByQueryUseCase,
    private val getDefaultIssuersUseCase: GetDefaultIssuersUseCase,
    private val getFavouriteIssuersUseCase: GetFavouriteIssuersUseCase,
    private val getIssuerByTickerUseCase: GetIssuerByTickerUseCase,
    private val getLocalIssuerByTickerUseCase: GetLocalIssuerByTickerUseCase,
    private val getLocalIssuersUseCase: GetLocalIssuersUseCase,
    private val getLocalFavouriteIssuersUseCase: GetLocalFavouriteIssuersUseCase,
    private val getMissingQuotesUseCase: GetMissingQuotesUseCase,
    private val getQuoteByTickerUseCase: GetQuoteByTickerUseCase,
    private val getEmptyQuoteByTickerUseCase: GetEmptyQuoteByTickerUseCase,
    private val setIssuerFavouriteUseCase: SetIssuerFavouriteUseCase,
    private val invalidateCacheUseCase: InvalidateCacheUseCase,
    favouriteIssuersChangedUseCase: FavouriteIssuersChangedUseCase,
    getRealTimeQuotesUseCase: GetRealTimeQuotesUseCase,
    missingQuotesUseCase: MissingQuotesUseCase,
    schedulersFactory: SchedulersFactory
) : StockListInteractor {

    private val _realTimeQuotes = PublishSubject.create<Collection<Quote>>()
    private val realTimeQuotesStorage = LinkedHashMap<String, Quote>()
    private fun addRealTimeQuote(quote: Quote) {
        realTimeQuotesStorage[quote.ticker] = quote
    }
    @Synchronized
    private fun addRealTimeQuotes(quotes: Collection<Quote>) {
        quotes.forEach(::addRealTimeQuote)
    }
    @Synchronized
    private fun snapshotRealTimeQuotes(): Collection<Quote> =
        mutableListOf<Quote>()
            .apply { addAll(realTimeQuotesStorage.values) }
            .also { realTimeQuotesStorage.clear() }

    init {
        getRealTimeQuotesUseCase.source(schedulersFactory.io())
            .filter { it.isNotEmpty() }
            .doOnNext { Timber.v("RT-quotes: ${it.joinToString { s -> "W-[${s.ticker}:${s.currentPrice}]" }}") }
            .subscribe(::addRealTimeQuotes, Timber::e)

        missingQuotesUseCase.source(schedulersFactory.io())
            .filter { it.isNotEmpty() }
            .subscribe(::addRealTimeQuotes, Timber::e)

        // periodically polls real-time quotes and passes them further on a client side
        Observable.timer(5000L, TimeUnit.MILLISECONDS)
            .flatMap {
                Observable.interval(1000L, TimeUnit.MILLISECONDS) // asynchronous with IO threads
                    .map { snapshotRealTimeQuotes() }
                    .filter { it.isNotEmpty() }
            }
            .subscribe(_realTimeQuotes::onNext, Timber::e)
    }

    override val favouriteIssuersChanged: Observable<IssuerFavourite> =
        favouriteIssuersChangedUseCase.source(schedulersFactory.io())

    override val realTimeQuotes: Observable<Collection<Quote>> = _realTimeQuotes.hide()

    override fun issuer(ticker: String, forceLocal: Boolean): Maybe<Issuer> =
        if (forceLocal) {
            getLocalIssuerByTickerUseCase.source { GetLocalIssuerByTickerUseCase.PARAM_TICKER of ticker }
        } else {
            getIssuerByTickerUseCase.source { GetIssuerByTickerUseCase.PARAM_TICKER of ticker }
        }

    override fun issuers(forceLocal: Boolean): Single<List<Issuer>> =
        if (forceLocal) {
            getLocalIssuersUseCase.source()
        } else {
            getDefaultIssuersUseCase.source()
        }

    override fun favouriteIssuers(forceLocal: Boolean): Single<List<Issuer>> =
        if (forceLocal) {
            getLocalFavouriteIssuersUseCase.source()
        } else {
            getFavouriteIssuersUseCase.source()
        }

    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        setIssuerFavouriteUseCase.source {
            SetIssuerFavouriteUseCase.PARAM_TICKER of ticker
            SetIssuerFavouriteUseCase.PARAM_IS_FAVOURITE of isFavourite
        }

    override fun quote(ticker: String): Single<Quote> =
        getQuoteByTickerUseCase.source { GetQuoteByTickerUseCase.PARAM_TICKER of ticker }

    override fun stocks(forceLocal: Boolean): Single<List<Stock>> {
        val issuersSource = issuers(forceLocal)
        return if (forceLocal) {
            getStocks(issuersSource)
        } else {
            getStocksBackground(issuersSource)
        }
    }

    override fun favouriteStocks(forceLocal: Boolean): Single<List<Stock>> {
        val issuersSource = favouriteIssuers(forceLocal)
        return if (forceLocal) {
            getStocks(issuersSource)
        } else {
            getStocksBackground(issuersSource)
        }
    }

    override fun findStocks(querySource: Observable<String>): Observable<List<Stock>> =
        querySource.switchMap { query ->
            findIssuersByQueryUseCase.source { FindIssuersByQueryUseCase.PARAM_QUERY of query }
                .let(::getStocks)
                .toObservable()
        }

    override fun invalidateCache(): Completable = invalidateCacheUseCase.source()

    // optimization method to postpone loading quotes and update them on UI asynchronously
    private fun getEmptyQuote(ticker: String): Single<Quote> =
        getEmptyQuoteByTickerUseCase.source { GetEmptyQuoteByTickerUseCase.PARAM_TICKER of ticker }

    /**
     * Get list of [Stock] for a given issuers (by [issuersSource]. [Quote]s will be first
     * tried to load from a local cache, otherwise - from the network.
     */
    private fun getStocks(issuersSource: Single<List<Issuer>>): Single<List<Stock>> =
        getStocksInternal(issuersSource, ::quote)

    /**
     * Get list of [Stock] for a given issuers (by [issuersSource]. [Quote]s will be first
     * set trivial for each [Stock] and their loading will be scheduled for later on background.
     * Then [Quote]s will come and update [Stock.price] and [Stock.priceDailyChange] asynchronously.
     */
    private fun getStocksBackground(issuersSource: Single<List<Issuer>>): Single<List<Stock>> =
        getStocksInternal(issuersSource, ::getEmptyQuote)

    private fun getStocksInternal(
        issuersSource: Single<List<Issuer>>,
        quoteSourceProducer: (ticker: String) -> Single<Quote>
    ): Single<List<Stock>> =
        issuersSource
            .flatMapObservable {
                Observable.fromIterable(it)
                    .concatMapSingle { issuer ->
                        quoteSourceProducer.invoke(issuer.ticker)
                            .map { quote ->
                                Stock(
                                    ticker = issuer.ticker,
                                    name = issuer.name,
                                    price = quote.currentPrice,
                                    priceDailyChange = quote.priceDayChange,
                                    logoUrl = issuer.logoUrl,
                                    isFavourite = issuer.isFavourite
                                )
                            }
                    }
            }
            .toList()
            .doOnSuccess { getMissingQuotes() }

    private fun getMissingQuotes() {
        getMissingQuotesUseCase.source()
            .subscribe({}, Timber::e)
    }
}
