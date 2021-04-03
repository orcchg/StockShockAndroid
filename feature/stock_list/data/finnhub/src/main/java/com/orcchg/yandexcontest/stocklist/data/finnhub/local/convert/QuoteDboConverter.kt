package com.orcchg.yandexcontest.stocklist.data.finnhub.local.convert

import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.NegativeSign
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.finnhub.local.model.QuoteDbo
import com.orcchg.yandexcontest.util.Converter
import dagger.Reusable
import javax.inject.Inject

@Reusable
class QuoteDboConverter @Inject constructor() : Converter<QuoteDbo, Quote> {

    override fun convert(from: QuoteDbo): Quote =
        Quote(
            ticker = from.ticker,
            currentPrice = Money.parse(from.currentPrice),
            maxPrice = Money.parse(from.maxPrice),
            minPrice = Money.parse(from.minPrice),
            openPrice = Money.parse(from.openPrice),
            prevClosePrice = Money.parse(from.prevClosePrice)
        )

    override fun revert(from: Quote): QuoteDbo =
        QuoteDbo(
            ticker = from.ticker,
            currentPrice = from.currentPrice.toString(NegativeSign),
            maxPrice = from.maxPrice.toString(NegativeSign),
            minPrice = from.minPrice.toString(NegativeSign),
            openPrice = from.openPrice.toString(NegativeSign),
            prevClosePrice = from.prevClosePrice.toString(NegativeSign)
        )
}
