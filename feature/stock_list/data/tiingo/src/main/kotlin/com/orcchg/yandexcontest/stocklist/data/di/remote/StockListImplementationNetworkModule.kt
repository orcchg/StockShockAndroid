package com.orcchg.yandexcontest.stocklist.data.di.remote

import com.orcchg.yandexcontest.core.network.tiingo.interceptor.AuthHeaderInterceptor
import com.orcchg.yandexcontest.core.network.tiingo.retrofit
import com.orcchg.yandexcontest.core.network.tiingo.scarlet
import com.tinder.scarlet.Scarlet
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
object StockListImplementationNetworkModule {

    @Provides
    @Reusable
    fun provideRetrofit(
        builder: Retrofit.Builder,
        client: OkHttpClient
    ): Retrofit =
        retrofit(builder, client, AuthHeaderInterceptor())

    @Provides
    @Reusable
    fun provideScarlet(
        builder: Scarlet.Builder,
        client: OkHttpClient
    ): Scarlet =
        scarlet(builder, client)
}
