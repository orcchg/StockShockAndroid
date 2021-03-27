package com.orcchg.yandexcontest.quotes.data.remote.model

import com.orcchg.yandexcontest.core.network.api.WsSubscribeType
import com.squareup.moshi.Json

data class WsSubscribeEntity(
    @Json(name = "type") val type: WsSubscribeType,
    @Json(name = "symbol") val ticker: String
)
