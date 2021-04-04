package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.remote.model.WsQuoteEntity
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class WsQuoteNetworkConverter @Inject constructor() : Converter<WsQuoteEntity, Quote> {

    override fun convert(from: WsQuoteEntity): Quote =
        Quote(
            ticker = from.ticker,
            currentPrice = from.price.money()
        )
}
