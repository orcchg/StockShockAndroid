package com.orcchg.yandexcontest.stocklist.data.di.remote

import com.orcchg.yandexcontest.stocklist.data.remote.interceptor.AuthHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
object StockListImplementationNetworkModule {

    @Provides
    @Reusable
    fun retrofit(
        builder: Retrofit.Builder,
        client: OkHttpClient,
        authHeaderInterceptor: AuthHeaderInterceptor
    ): Retrofit =
        client.newBuilder()
            .addInterceptor(authHeaderInterceptor)
            .build()
            .let { client ->
                builder
                    .client(client)
                    .baseUrl("https://api.tiingo.com/tiingo/")
                    .build()
            }
}
