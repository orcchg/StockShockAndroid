package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.stocklist.data.remote.StockListRest
import dagger.Module
import retrofit2.Retrofit
import retrofit2.create

@Module
@InternalBindings
object StockListNetworkModule {

    fun rest(retrofit: Retrofit): StockListRest = retrofit.create()
}
