package com.orcchg.yandexcontest.stocklist.data.api

import com.orcchg.yandexcontest.stocklist.api.model.Index
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface StockListRepository {

    val favouriteIssuersChanged: Observable<IssuerFavourite>

    /**
     * Holds a set of [Quote]s that had been missing before and then successfully fetched
     * from the network to be applied later on a client side.
     */
    val missingQuotes: Observable<Collection<Quote>>

    /**
     * Retrieves a single [Issuer] corresponding to a given [ticker].
     */
    fun issuer(ticker: String): Maybe<Issuer>

    /**
     * Retrieves list of [Issuer] corresponding to a given [Index.name].
     */
    fun defaultIssuers(): Single<List<Issuer>>

    fun favouriteIssuers(): Single<List<Issuer>>

    fun localIssuer(ticker: String): Maybe<Issuer>

    fun localIssuers(): Single<List<Issuer>>

    fun localFavouriteIssuers(): Single<List<Issuer>>

    fun findIssuers(query: String): Single<List<Issuer>>

    fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable

    /**
     * Retrieves [Quote] corresponding to a given [ticker].
     */
    fun quote(ticker: String): Single<Quote>

    /**
     * Very quickly retrieves [Quote] corresponding to a given [ticker] w/o data.
     * The data to be filled later.
     */
    fun emptyQuote(ticker: String): Single<Quote>

    /**
     * Retrieves [Quote]s which have failed to fetch from the network.
     *
     * Such [Quote]s will be fetched in a background and applied later on a client side.
     */
    fun getMissingQuotes(): Completable

    /**
     * Invalidates internal data structures.
     */
    fun invalidateCache(): Completable
}
