package com.orcchg.yandexcontest.fake

import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coremodel.Locale as locale
import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.fake.data.FindStocksManager
import com.orcchg.yandexcontest.fake.data.fakeIssuers
import com.orcchg.yandexcontest.fake.data.getIssuer
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.util.toListNoDuplicates
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

class FakeStockListInteractor @Inject constructor(
    private val findStocksManager: FindStocksManager
) : StockListInteractor {

    private val favouriteIssuers = mutableListOf<Issuer>().apply {
        add(Issuer(name = "Apple Inc.", ticker = "AAPL", isFavourite = true, country = "United States", currency = Currency.getInstance(Locale.US)))
        add(Issuer(name = "Microsoft Corporation", ticker = "MSFT", isFavourite = true, country = "United States", currency = Currency.getInstance(Locale.US)))
        add(Issuer(name = "Tesla Motors", ticker = "TSLA", isFavourite = true, country = "United States", currency = Currency.getInstance(Locale.US)))
    }

    override val favouriteIssuersChanged: Observable<IssuerFavourite> = Observable.empty()
    override val realTimeQuotes: Observable<Collection<Quote>> = Observable.empty()

    override fun issuers(forceLocal: Boolean): Single<List<Issuer>> = Single.just(fakeIssuers)

    override fun favouriteIssuers(forceLocal: Boolean): Single<List<Issuer>> =
        Single.just(favouriteIssuers)

    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        Completable.complete() // operation not supported

    override fun quote(ticker: String): Single<Quote> =
        Single.just(
            when (ticker) {
                "YNDX" ->
                    Currency.getInstance(locale.RUSSIA).let {
                        Quote(ticker, 4764.6.money(it), 4712.money(it))
                    }
                "AAPL" -> Quote(ticker, 131.93.money(), 129.1.money())
                "GOOGL" -> Quote(ticker, 1825.money(), 1802.money())
                "AMZN" -> Quote(ticker, 3204.money(), 3254.2.money())
                "BAC" -> Quote(ticker, 24.7.money(), 23.9.money())
                "MSFT" -> Quote(ticker, 234.money(), 233.11.money())
                "TSLA" -> Quote(ticker, 894.21.money(), 888.8.money())
                "MA" -> Quote(ticker, 519.money(), 521.7.money())
                "FB" -> Quote(ticker, 276.money(), 283.1.money())
                else -> Quote(ticker)
            }
        )

    override fun stocks(forceLocal: Boolean): Single<List<Stock>> =
        getStocks(issuersSource = issuers(forceLocal))

    override fun favouriteStocks(forceLocal: Boolean): Single<List<Stock>> =
        getStocks(issuersSource = favouriteIssuers(forceLocal))

    override fun findStocks(querySource: Observable<String>): Observable<List<Stock>> =
        querySource.switchMap { query ->
            val relevant = findStocksManager.findByPrefix(query)
            Observable.fromIterable(relevant)
                .map(::getIssuer)
                .toListNoDuplicates()
                .let(::getStocks)
                .toObservable()
        }

    override fun invalidateCache(stockSelection: StockSelection): Completable =
        Completable.complete() // operation not supported

    private fun getStocks(issuersSource: Single<List<Issuer>>): Single<List<Stock>> =
        issuersSource
            .flatMapObservable {
                Observable.fromIterable(it)
                    .flatMapSingle { issuer ->
                        quote(issuer.ticker)
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
}
