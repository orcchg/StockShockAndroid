package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.stocklist.data.finnhub.remote.StockListRest
import com.orcchg.yandexcontest.stocklist.data.finnhub.remote.StockListWebSocket
import com.tinder.scarlet.Scarlet
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
@InternalBindings
object StockListNetworkModule {

    @Provides
    fun rest(retrofit: Retrofit): StockListRest = retrofit.create()

    @Provides
    fun webSocket(scarlet: Scarlet): StockListWebSocket = scarlet.create()
}
