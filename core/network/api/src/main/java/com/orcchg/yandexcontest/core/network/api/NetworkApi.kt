package com.orcchg.yandexcontest.core.network.api

import com.orcchg.yandexcontest.coredi.Api
import retrofit2.Retrofit

interface NetworkApi : Api {

    fun retrofit(): Retrofit
}
