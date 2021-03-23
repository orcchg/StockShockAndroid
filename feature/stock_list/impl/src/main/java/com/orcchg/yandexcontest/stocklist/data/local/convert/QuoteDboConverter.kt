package com.orcchg.yandexcontest.stocklist.data.local.convert

import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.NegativeSign
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.local.model.QuoteDbo
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class QuoteDboConverter @Inject constructor() : Converter<QuoteDbo, Quote> {

    override fun convert(from: QuoteDbo): Quote =
        Quote(
            currentPrice = Money.parse(from.currentPrice),
            maxPrice = Money.parse(from.maxPrice),
            minPrice = Money.parse(from.minPrice),
            openPrice = Money.parse(from.openPrice),
            prevClosePrice = Money.parse(from.prevClosePrice)
        )

    fun revert(ticker: String, quote: Quote): QuoteDbo =
        QuoteDbo(
            ticker = ticker,
            currentPrice = quote.currentPrice.toString(NegativeSign),
            maxPrice = quote.maxPrice.toString(NegativeSign),
            minPrice = quote.minPrice.toString(NegativeSign),
            openPrice = quote.openPrice.toString(NegativeSign),
            prevClosePrice = quote.prevClosePrice.toString(NegativeSign)
        )
}
