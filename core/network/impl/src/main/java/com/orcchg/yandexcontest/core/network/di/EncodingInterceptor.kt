package com.orcchg.yandexcontest.core.network.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class EncodingInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
            .replace("%5E", "^")
            .replace("%26", "&")
            .replace("%3D", "=")

        return request.newBuilder().url(url).build().let(chain::proceed)
    }
}
