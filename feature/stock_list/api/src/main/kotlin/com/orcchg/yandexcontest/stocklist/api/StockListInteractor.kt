package com.orcchg.yandexcontest.stocklist.api

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface StockListInteractor {

    val favouriteIssuersChanged: Observable<IssuerFavourite>
    val realTimeQuotes: Observable<Collection<Quote>>

    fun issuer(ticker: String, forceLocal: Boolean = false): Maybe<Issuer>

    fun issuers(forceLocal: Boolean = false): Single<List<Issuer>>

    fun favouriteIssuers(forceLocal: Boolean = false): Single<List<Issuer>>

    fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable

    fun quote(ticker: String): Single<Quote>

    fun stocks(forceLocal: Boolean = false): Single<List<Stock>>

    fun favouriteStocks(forceLocal: Boolean = false): Single<List<Stock>>

    fun findStocks(querySource: Observable<String>): Observable<List<Stock>>

    fun invalidateCache(): Completable
}
