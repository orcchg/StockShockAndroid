package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json

data class IssuerEntity(
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "exchangeCode") val exchange: String,
    @Json(name = "ticker") val ticker: String,
)
