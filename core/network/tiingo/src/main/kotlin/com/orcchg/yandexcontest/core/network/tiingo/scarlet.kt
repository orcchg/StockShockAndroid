package com.orcchg.yandexcontest.core.network.tiingo

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient

fun scarlet(
    builder: Scarlet.Builder,
    client: OkHttpClient
): Scarlet =
    builder
        .webSocketFactory(client.newWebSocketFactory("wss://api.tiingo.com/iex"))
        .build()
