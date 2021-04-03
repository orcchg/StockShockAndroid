package com.orcchg.yandexcontest.stocklist.data.finnhub.remote.model

import com.squareup.moshi.Json

data class WsQuoteListEntity(
    @Json(name = "type") val type: String,
    @Json(name = "data") val data: List<WsQuoteEntity>
)
