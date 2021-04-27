package com.orcchg.yandexcontest.stocklist.data.remote

import com.orcchg.yandexcontest.stocklist.data.remote.model.IndexEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.IssuerEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StockListRest {

    @GET("index/constituents")
    fun index(@Query("symbol") symbol: String): Single<IndexEntity>

    @GET("stock/profile2")
    fun issuer(@Query("symbol") ticker: String): Single<IssuerEntity>

    @GET("quote")
    fun quote(@Query("symbol", encoded = true) ticker: String): Single<QuoteEntity>
}
