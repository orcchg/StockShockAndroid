package com.orcchg.yandexcontest.stocklist.data.fake.local.convert

import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.fake.local.model.QuoteDbo
import com.orcchg.yandexcontest.util.Converter
import dagger.Reusable
import javax.inject.Inject

@Reusable
class QuoteDboConverter @Inject constructor() : Converter<QuoteDbo, Quote> {

    override fun convert(from: QuoteDbo): Quote =
        Quote(
            ticker = from.ticker,
            currentPrice = Money.parse(from.currentPrice),
            prevClosePrice = Money.parse(from.prevClosePrice)
        )
}
