package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IndexEntity(
    @Json(name = "constituents") val tickers: List<String>,
    @Json(name = "symbol") val name: String
)
