package com.orcchg.yandexcontest.stockdetails.data.local.convert

import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.NegativeSign
import com.orcchg.yandexcontest.stockdetails.api.model.Candle
import com.orcchg.yandexcontest.stockdetails.data.local.model.CandleDbo
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class CandleDboConverter @Inject constructor() : Converter<CandleDbo, Candle> {

    override fun convert(from: CandleDbo): Candle =
        Candle(
            ticker = from.ticker,
            openPrice = Money.parse(from.openPrice),
            maxPrice = Money.parse(from.maxPrice),
            minPrice = Money.parse(from.minPrice),
            closePrice = Money.parse(from.closePrice),
            resolution = Candle.Resolution.parse(from.resolution),
            volume = from.volume,
            ts = from.timestamp
        )

    override fun revert(from: Candle): CandleDbo =
        CandleDbo(
            ticker = from.ticker,
            openPrice = from.openPrice.toString(NegativeSign),
            maxPrice = from.maxPrice.toString(NegativeSign),
            minPrice = from.minPrice.toString(NegativeSign),
            closePrice = from.closePrice.toString(NegativeSign),
            resolution = from.resolution.v,
            volume = from.volume,
            timestamp = from.ts
        )
}
