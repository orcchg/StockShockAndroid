package com.orcchg.yandexcontest.stocklist.data.remote

import com.orcchg.yandexcontest.stocklist.data.model.IssuerEntity
import com.orcchg.yandexcontest.stocklist.data.model.QuoteEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StockListRest {

    @GET("stock/profile2")
    fun issuer(@Query("symbol") ticker: String): Single<IssuerEntity>

    @GET("quote")
    fun quote(@Query("symbol") ticker: String): Single<QuoteEntity>
}
