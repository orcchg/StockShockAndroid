package com.orcchg.yandexcontest.stocklist.data.di.remote

import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object StockListImplementationNetworkModule {

    @Provides
    @Reusable
    fun retrofit(builder: Retrofit.Builder): Retrofit =
        builder
            .baseUrl("https://finnhub.io/api/v1/")
            .build()
}
