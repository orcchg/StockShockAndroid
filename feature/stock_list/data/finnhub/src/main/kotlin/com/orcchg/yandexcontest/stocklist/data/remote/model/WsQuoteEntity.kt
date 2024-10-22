package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class WsQuoteEntity(
    @Json(name = "s") val ticker: String,
    @Json(name = "p") val price: BigDecimal = BigDecimal.ZERO,
    @Json(name = "t") val timestamp: Long,
    @Json(name = "v") val volume: Double = 0.0
)
