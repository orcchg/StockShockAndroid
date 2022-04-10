package com.orcchg.yandexcontest.core.network.finnhub

import com.orcchg.yandexcontest.core.network.finnhub.interceptor.API_KEY
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient

fun scarlet(
    builder: Scarlet.Builder,
    client: OkHttpClient
): Scarlet =
    builder
        .webSocketFactory(client.newWebSocketFactory("wss://ws.finnhub.io?token=$API_KEY"))
        .build()
