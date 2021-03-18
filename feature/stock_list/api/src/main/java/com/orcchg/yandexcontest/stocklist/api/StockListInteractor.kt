package com.orcchg.yandexcontest.stocklist.api

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import io.reactivex.Single

interface StockListInteractor {

    fun issuers(): Single<List<Issuer>>

    fun favouriteIssuers(): Single<List<Issuer>>

    fun quote(ticker: String): Single<Quote>

    fun stocks(): Single<List<Stock>>

    fun favouriteStocks(): Single<List<Stock>>

    fun findStocks(query: String): Single<List<Stock>>
}
