package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class QuoteNetworkConverter @Inject constructor() : Converter<QuoteEntity, Quote> {

    override fun convert(from: QuoteEntity): Quote =
        Quote(
            currentPrice = from.currentPrice.money(),
            prevClosePrice = from.prevClosePrice.money()
        )
}
