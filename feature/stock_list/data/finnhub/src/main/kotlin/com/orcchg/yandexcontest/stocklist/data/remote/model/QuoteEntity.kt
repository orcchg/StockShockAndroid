package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class QuoteEntity(
    @Json(name = "c") val currentPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "h") val maxPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "l") val minPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "o") val openPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "pc") val prevClosePrice: BigDecimal = BigDecimal.ZERO
)
