package com.orcchg.yandexcontest.core.network.di

import com.orcchg.yandexcontest.core.network.api.WsSubscribeType
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

object MoshiAdapters {

    @FromJson
    fun subscribeTypeFromJson(string: String): WsSubscribeType =
        WsSubscribeType.valueOf(string.toUpperCase(Locale.ROOT))

    @ToJson
    fun subscribeTypeToJson(data: WsSubscribeType): String =
        data.name.toLowerCase(Locale.ROOT)
}