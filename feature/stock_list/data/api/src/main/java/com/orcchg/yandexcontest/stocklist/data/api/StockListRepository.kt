package com.orcchg.yandexcontest.stocklist.data.api

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface StockListRepository {

    val favouriteIssuersChanged: Observable<IssuerFavourite>
    val missingQuotes: Observable<Collection<Quote>>

    fun issuer(ticker: String): Maybe<Issuer>

    fun defaultIssuers(): Single<List<Issuer>>

    fun favouriteIssuers(): Single<List<Issuer>>

    fun localIssuer(ticker: String): Maybe<Issuer>

    fun localIssuers(): Single<List<Issuer>>

    fun localFavouriteIssuers(): Single<List<Issuer>>

    fun findIssuers(query: String): Single<List<Issuer>>

    fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable

    fun quote(ticker: String): Single<Quote>

    fun emptyQuote(ticker: String): Single<Quote>

    fun getMissingQuotes(): Completable

    fun invalidateCache(): Completable
}
