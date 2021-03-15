package com.orcchg.yandexcontest.stocklist.demo.convert

import androidx.annotation.DrawableRes
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.demo.R
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

internal class IssuerToStockVoConverter @Inject constructor() : Converter<Issuer, StockVO> {

    // TODO: prices, isFavourite
    override fun convert(from: Issuer): StockVO =
        StockVO(
            name = from.name,
            price = "$72.16",
            priceDailyChange = "+$ 0.12 (1,15%)",
            ticker = from.ticker,
            logoResId = resolveLogoResIdByTicker(from.ticker),
            logoUrl = from.logoUrl,
            isFavourite = false
        )

    @DrawableRes
    private fun resolveLogoResIdByTicker(ticker: String): Int =
        when (ticker) {
            "AAPL" -> R.drawable.stock_apple_logo
            "AMZN" -> R.drawable.stock_amazon_logo
            "BAC" -> R.drawable.stock_bac_logo
            "GOOGL" -> R.drawable.stock_google_logo
            "MA" -> R.drawable.stock_mastercard_logo
            "MSFT" -> R.drawable.stock_microsoft_logo
            "TSLA" -> R.drawable.stock_tesla_logo
            "YNDX" -> R.drawable.stock_yandex_logo
            else -> 0
        }
}
