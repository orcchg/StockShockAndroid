package com.orcchg.yandexcontest.core.network.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .addHeader("X-Finnhub-Token", API_KEY)
            .build()
            .let(chain::proceed)
}

const val API_KEY = "c1bs2j748v6sp0s54qp0"
