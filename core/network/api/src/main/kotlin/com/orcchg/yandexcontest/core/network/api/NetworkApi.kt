package com.orcchg.yandexcontest.core.network.api

import com.orcchg.yandexcontest.coredi.Api
import com.tinder.scarlet.Scarlet
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface NetworkApi : Api {

    fun okHttpClient(): OkHttpClient

    fun retrofit(): Retrofit.Builder

    fun scarlet(): Scarlet.Builder
}
