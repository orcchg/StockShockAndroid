package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json

data class IndexEntity(
    @Json(name = "constituents") val tickers: List<String>,
    @Json(name = "symbol") val name: String
)
