package com.orcchg.yandexcontest.fake

import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
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
            )
        ))
}
