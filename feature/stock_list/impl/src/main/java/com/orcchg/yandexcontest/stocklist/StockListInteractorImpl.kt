package com.orcchg.yandexcontest.stocklist

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import io.reactivex.Single
import javax.inject.Inject

class StockListInteractorImpl @Inject constructor() : StockListInteractor {

    override fun issuers(): Single<List<Issuer>> = Single.just(emptyList())

    override fun quote(ticker: String): Single<Quote> = Single.just(Quote(1725.0.money(), 2.56.money()))

    override fun stocks(): Single<List<Stock>> = Single.just(emptyList())
}
