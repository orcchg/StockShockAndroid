package com.orcchg.yandexcontest.core.network.api

import com.orcchg.yandexcontest.coredi.Api
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import retrofit2.Retrofit

interface NetworkApi : Api {

    fun moshi(): Moshi

    fun retrofit(): Retrofit

    fun scarlet(): Scarlet
}
