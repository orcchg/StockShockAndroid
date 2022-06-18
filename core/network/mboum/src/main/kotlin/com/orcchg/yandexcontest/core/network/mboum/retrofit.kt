package com.orcchg.yandexcontest.core.network.mboum

import com.orcchg.yandexcontest.core.network.mboum.interceptor.AuthHeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

fun retrofit(
    builder: Retrofit.Builder,
    client: OkHttpClient,
    authHeaderInterceptor: AuthHeaderInterceptor
): Retrofit =
    client.newBuilder()
        .addInterceptor(authHeaderInterceptor)
        .build()
        .let {
            builder
                .client(it)
                .baseUrl("https://mboum.com/api/v1/")
                .build()
        }
