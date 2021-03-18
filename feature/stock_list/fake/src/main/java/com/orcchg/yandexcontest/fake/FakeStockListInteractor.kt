package com.orcchg.yandexcontest.fake

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FakeStockListInteractor @Inject constructor() : StockListInteractor {

    override fun issuers(): Single<List<Issuer>> =
        Single.just(listOf(
            Issuer(
                name = "Yandex, LLC",
                ticker = "YNDX"
            ),
            Issuer(
                name = "Apple Inc.",
                ticker = "AAPL"
            ),
            Issuer(
                name = "Alphabet Class A",
                ticker = "GOOGL"
            ),
            Issuer(
                name = "Amazon.com",
                ticker = "AMZN"
            ),
            Issuer(
                name = "Bank of America Corp",
                ticker = "BAC"
            ),
            Issuer(
                name = "Microsoft Corporation",
                ticker = "MSFT"
            ),
            Issuer(
                name = "Tesla Motors",
                ticker = "TSLA"
            ),
            Issuer(
                name = "Mastercard",
                ticker = "MA"
            ),
            Issuer(
                name = "Facebook",
                ticker = "FB"
            ),
            Issuer(
                name = "Gazprom",
                ticker = "GAZP"
            ),
            Issuer(
                name = "Rosneft",
                ticker = "ROSN"
            ),
            Issuer(
                name = "GMK Nor Nickel",
                ticker = "GMKN"
            ),
            Issuer(
                name = "Sberbank",
                ticker = "SBER"
            ),
            Issuer(
                name = "Mail.ru Group",
                ticker = "MAIL"
            )
        ))

    override fun favouriteIssuers(): Single<List<Issuer>> =
        Single.just(listOf(
            Issuer(
                name = "Apple Inc.",
                ticker = "AAPL"
            ),
            Issuer(
                name = "Microsoft Corporation",
                ticker = "MSFT"
            ),
            Issuer(
                name = "Tesla Motors",
                ticker = "TSLA"
            )
        ))

    override fun quote(ticker: String): Single<Quote> =
        Single.just(
            when (ticker) {
                "YNDX" -> Quote(4764.6.money(), 4712.money())
                "AAPL" -> Quote(131.93.money(), 129.1.money())
                "GOOGL" -> Quote(1825.money(), 1802.money())
                "AMZN" -> Quote(3204.money(), 3254.2.money())
                "BAC" -> Quote(24.7.money(), 23.9.money())
                "MSFT" -> Quote(234.money(), 233.11.money())
                "TSLA" -> Quote(894.21.money(), 888.8.money())
                "MA" -> Quote(519.money(), 521.7.money())
                "FB" -> Quote(276.money(), 283.1.money())
                else -> Quote()
            }
        )

    override fun stocks(): Single<List<Stock>> =
        getStocks(issuersSource = issuers())

    override fun favouriteStocks(): Single<List<Stock>> =
        getStocks(issuersSource = favouriteIssuers())

    override fun findStocks(query: String): Single<List<Stock>> =
        Single.just(listOf(
            Stock(
                id = "APPN",
                name = "Appian Corp.",
                price = 217.08.money()
            ),
            Stock(
                id = "AAPL",
                name = "Apple Inc.",
                price = 131.93.money()
            ),
            Stock(
                id = "APPF",
                name = "Appfolio Inc.",
                price = 152.54.money()
            ),
            Stock(
                id = "APPI",
                name = "Appi Inc.",
                price = 26.27.money()
            )
        ))

    private fun getStocks(issuersSource: Single<List<Issuer>>): Single<List<Stock>> =
        issuersSource
            .flatMapObservable {
                Observable.fromIterable(it)
                    .flatMapSingle { issuer ->
                        quote(issuer.ticker)
                            .map { quote ->
                                Stock(
                                    id = issuer.ticker,
                                    name = issuer.name,
                                    price = quote.currentPrice,
                                    priceDailyChange = quote.currentPrice - quote.prevClosePrice,
                                    logoUrl = issuer.logoUrl
                                )
                            }
                    }
            }
            .toList()
}
