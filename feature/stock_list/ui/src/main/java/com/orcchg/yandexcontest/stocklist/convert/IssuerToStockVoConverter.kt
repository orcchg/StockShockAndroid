package com.orcchg.yandexcontest.stocklist.convert

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class IssuerToStockVoConverter @Inject constructor() : Converter<Issuer, StockVO> {

    // TODO: prices, isFavourite
    override fun convert(from: Issuer): StockVO =
        StockVO(
            name = from.name,
            price = "",
            priceDailyChange = "",
            ticker = from.ticker,
            url = from.logoUrl,
            isFavourite = false
        )
}
