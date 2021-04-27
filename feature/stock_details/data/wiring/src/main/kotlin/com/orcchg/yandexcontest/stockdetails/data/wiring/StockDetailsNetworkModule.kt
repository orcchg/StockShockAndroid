package com.orcchg.yandexcontest.stockdetails.data.wiring

import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.stockdetails.data.remote.StockDetailsRest
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
@InternalBindings
object StockDetailsNetworkModule {

    @Provides
    fun rest(retrofit: Retrofit): StockDetailsRest = retrofit.create()
}
