package com.orcchg.yandexcontest.stocklist.data.remote

import com.orcchg.yandexcontest.stocklist.data.remote.model.WsQuoteListEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.WsSubscribeEntity
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface StockListWebSocket {

    @Send
    fun subscribe(request: WsSubscribeEntity)

    @Receive
    fun quotes(): Flowable<WsQuoteListEntity>

    @Receive
    fun connectionEvents(): Flowable<WebSocket.Event>
}
