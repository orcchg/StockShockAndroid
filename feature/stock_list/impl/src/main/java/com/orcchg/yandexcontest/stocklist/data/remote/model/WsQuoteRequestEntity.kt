package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json

data class WsQuoteRequestEntity(
    @Json(name = "type") val type: String = "subscribe",
    @Json(name = "symbol") val ticker: String
)
