package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.coremodel.Quote
import com.orcchg.yandexcontest.stocklist.data.remote.model.WsQuoteEntity
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

@Deprecated("quotes")
class WsQuoteNetworkConverter @Inject constructor() : Converter<WsQuoteEntity, Quote> {

    override fun convert(from: WsQuoteEntity): Quote =
        Quote(
            ticker = from.ticker,
            currentPrice = from.price.money()
        )
}
