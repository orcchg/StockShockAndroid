package com.orcchg.yandexcontest.stocklist.domain

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import io.reactivex.Completable
import io.reactivex.Single

interface StockListRepository {

    fun defaultIssuers(): Single<List<Issuer>>

    fun favouriteIssuers(): Single<List<Issuer>>

    fun findIssuers(query: String): Single<List<Issuer>>

    fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable

    fun quote(ticker: String): Single<Quote>
}
