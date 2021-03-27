package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.coremodel.Quote
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import javax.inject.Inject

class QuoteNetworkConverter @Inject constructor() {

    fun convert(ticker: String, from: QuoteEntity): Quote =
        Quote(
            ticker = ticker,
            currentPrice = from.currentPrice.money(),
            maxPrice = from.maxPrice.money(),
            minPrice = from.minPrice.money(),
            openPrice = from.openPrice.money(),
            prevClosePrice = from.prevClosePrice.money()
        )
}
