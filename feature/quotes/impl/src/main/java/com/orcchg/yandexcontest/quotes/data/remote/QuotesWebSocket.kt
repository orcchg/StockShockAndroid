package com.orcchg.yandexcontest.quotes.data.remote

import com.orcchg.yandexcontest.quotes.data.remote.model.WsQuoteListEntity
import com.orcchg.yandexcontest.quotes.data.remote.model.WsSubscribeEntity
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface QuotesWebSocket {

    @Send
    fun subscribe(request: WsSubscribeEntity)

    @Receive
    fun quotes(): Flowable<WsQuoteListEntity>

    @Receive
    fun connectionEvents(): Flowable<WebSocket.Event>
}
