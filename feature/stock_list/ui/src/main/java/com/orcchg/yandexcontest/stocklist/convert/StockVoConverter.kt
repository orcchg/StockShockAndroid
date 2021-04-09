package com.orcchg.yandexcontest.stocklist.convert

import com.orcchg.yandexcontest.coremodel.formatPriceChange
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.Converter
import com.orcchg.yandexcontest.util.ResourceSupplier
import javax.inject.Inject

class StockVoConverter @Inject constructor(
    private val logoResSupplier: ResourceSupplier
) : Converter<Stock, StockVO> {

    override fun convert(from: Stock): StockVO =
        StockVO(
            name = from.name,
            price = from.price.toString(),
            priceDailyChange = formatPriceChange(from.price, from.priceDailyChange),
            ticker = from.ticker,
            logoResId = logoResSupplier.getResIdByKey(from.ticker),
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )
}
