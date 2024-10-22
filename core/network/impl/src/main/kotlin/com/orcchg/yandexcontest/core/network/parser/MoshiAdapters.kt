package com.orcchg.yandexcontest.core.network.parser

import com.orcchg.yandexcontest.core.network.api.WsSubscribeType
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.Locale

@Suppress("Unused")
internal object MoshiAdapters {

    @FromJson
    fun subscribeTypeFromJson(string: String): WsSubscribeType =
        WsSubscribeType.valueOf(string.uppercase(Locale.ROOT))

    @ToJson
    fun subscribeTypeToJson(data: WsSubscribeType): String =
        data.name.lowercase(Locale.ROOT)
}
