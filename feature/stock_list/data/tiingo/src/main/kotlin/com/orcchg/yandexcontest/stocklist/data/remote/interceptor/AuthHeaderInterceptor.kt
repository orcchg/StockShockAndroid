package com.orcchg.yandexcontest.stocklist.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .addHeader("Authorization", "Token $API_KEY")
            .build()
            .let(chain::proceed)
}

private const val API_KEY = "13dfd7509c738eacd8ba4f1738e81b299971d188"
