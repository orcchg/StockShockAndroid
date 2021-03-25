package com.orcchg.yandexcontest.core.network.api

import com.orcchg.yandexcontest.coredi.Api
import com.tinder.scarlet.Scarlet
import retrofit2.Retrofit

interface NetworkApi : Api {

    fun retrofit(): Retrofit

    fun scarlet(): Scarlet
}
