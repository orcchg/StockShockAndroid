package com.orcchg.yandexcontest.stocklist.data.di.remote

import com.tinder.scarlet.Scarlet
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object StockListImplementationNetworkModule {

    @Provides
    @Reusable
    fun retrofit(builder: Retrofit.Builder): Retrofit = builder.build()

    @Provides
    @Reusable
    fun scarlet(builder: Scarlet.Builder): Scarlet = builder.build()
}
