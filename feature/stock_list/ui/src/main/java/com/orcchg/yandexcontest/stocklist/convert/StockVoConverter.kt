package com.orcchg.yandexcontest.stocklist.convert

import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class StockVoConverter @Inject constructor() : Converter<Stock, StockVO> {

    override fun convert(from: Stock): StockVO =
        StockVO(
            name = from.name,
            price = from.price.toString(),
            priceDailyChange = from.priceDailyChange.toString(),
            ticker = from.id,
            logoUrl = from.url,
            isFavourite = false
        )
}
