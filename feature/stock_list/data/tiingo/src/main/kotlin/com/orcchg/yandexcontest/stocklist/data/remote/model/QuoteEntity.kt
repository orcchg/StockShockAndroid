package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json
import java.math.BigDecimal
import java.math.BigInteger

data class QuoteEntity(
    @Json(name = "date") val date: String? = null,
    @Json(name = "close") val currentPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "high") val maxPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "low") val minPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "open") val openPrice: BigDecimal = BigDecimal.ZERO,
    @Json(name = "volume") val volume: BigInteger = BigInteger.ZERO,
    val prevClosePrice: BigDecimal = BigDecimal.ZERO // calculated manually
)
