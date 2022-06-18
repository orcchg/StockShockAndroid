package com.orcchg.yandexcontest.stocklist.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface StockListWebSocket {

    @GET("hi/history")
    fun historicalData(
        @Query("symbol") ticker: String,

    )
}
