package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WsQuoteListEntity(
    @Json(name = "type") val type: String,
    @Json(name = "data") val data: List<WsQuoteEntity>
)
