package com.orcchg.yandexcontest.stocklist

import android.annotation.SuppressLint
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
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
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalFavouriteIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetMissingQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetQuoteByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetRealTimeQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.InvalidateCacheUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.MissingQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.SetIssuerFavouriteUseCase
import io.reactivex.Completable
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

    override fun stocks(forceLocal: Boolean): Single<List<Stock>> =
        getStocks(issuersSource = issuers(forceLocal))

    override fun favouriteStocks(forceLocal: Boolean): Single<List<Stock>> =
        getStocks(issuersSource = favouriteIssuers(forceLocal))

    override fun findStocks(querySource: Observable<String>): Observable<List<Stock>> =
        querySource.switchMap { query ->
            findIssuersByQueryUseCase.source { FindIssuersByQueryUseCase.PARAM_QUERY of query }
                .let(::getStocks)
                .toObservable()
        }

    override fun invalidateCache(stockSelection: StockSelection): Completable =
        invalidateCacheUseCase.source { InvalidateCacheUseCase.PARAM_STOCK_SELECTION of stockSelection }

    @Suppress("Unused")
    private fun getEmptyQuote(ticker: String): Single<Quote> =
        getEmptyQuoteByTickerUseCase.source { GetEmptyQuoteByTickerUseCase.PARAM_TICKER of ticker }

    private fun getStocks(issuersSource: Single<List<Issuer>>): Single<List<Stock>> =
        issuersSource
            .flatMapObservable {
                Observable.fromIterable(it)
                    .concatMapSingle { issuer ->
                        getEmptyQuote(issuer.ticker)
                            .map { quote ->
                                Stock(
                                    ticker = issuer.ticker,
                                    name = issuer.name,
                                    price = quote.currentPrice,
                                    priceDailyChange = quote.currentPrice - quote.prevClosePrice,
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
