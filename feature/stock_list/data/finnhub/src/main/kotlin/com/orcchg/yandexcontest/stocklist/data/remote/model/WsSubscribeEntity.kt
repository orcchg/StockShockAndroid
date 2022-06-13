package com.orcchg.yandexcontest.stocklist.data.remote.model

import com.orcchg.yandexcontest.core.network.api.WsSubscribeType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WsSubscribeEntity(
    @Json(name = "type") val type: WsSubscribeType,
    @Json(name = "symbol") val ticker: String
)
