package com.orcchg.yandexcontest.stocklist.data.remote

import com.orcchg.yandexcontest.stocklist.data.remote.model.WsQuoteListEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.WsQuoteRequestEntity
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface StockListWebSocket {
    @Send
    fun openConnection(request: WsQuoteRequestEntity)

    @Receive
    fun quotes(): Flowable<WsQuoteListEntity>

    @Receive
    fun openConnectionEvent(): Flowable<WebSocket.Event.OnConnectionOpened<*>>
}
