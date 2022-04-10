package com.orcchg.yandexcontest.stocklist.data.remote

import com.orcchg.yandexcontest.stocklist.data.remote.model.IssuerEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockListRest {

    @GET("daily/{ticker}")
    fun issuer(@Path("ticker") ticker: String): Single<IssuerEntity>

    @GET("daily/{ticker}/prices")
    fun quote(
        @Path("ticker") ticker: String,
        @Query("startDate", encoded = true) startDate: String
    ): Single<List<QuoteEntity>>
}
