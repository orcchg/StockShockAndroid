package com.orcchg.yandexcontest.stocklist.convert

import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.RealNoZeroSign
import com.orcchg.yandexcontest.coremodel.div
import com.orcchg.yandexcontest.coremodel.times
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
            ticker = from.id,
            logoResId = logoResSupplier.getResIdByKey(from.id),
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )

    companion object {
        fun formatPriceChange(price: Money, change: Money): String {
            val percentage = if (price.isZero()) 0.00 else ((change * 100.0) / price).amount()
            return "${change.toString(RealNoZeroSign(change.isZero()))} ($percentage%)"
        }
    }
}
