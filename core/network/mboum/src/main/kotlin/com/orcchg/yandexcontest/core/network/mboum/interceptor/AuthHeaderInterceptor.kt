package com.orcchg.yandexcontest.core.network.mboum.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .addHeader("X-Mboum-Secret", API_KEY)
            .build()
            .let(chain::proceed)
}

private const val API_KEY = "tjT7EnUvk0XMVCI6ATqbxgbH3MMGhR0HwvtcYhxA3xGOYDYGJl23cuofUhZv"
