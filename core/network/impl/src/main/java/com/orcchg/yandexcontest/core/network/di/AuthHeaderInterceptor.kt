package com.orcchg.yandexcontest.core.network.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .addHeader("X-Finnhub-Token", "c1bs2j748v6sp0s54qp0")
            .build()
            .let(chain::proceed)
}