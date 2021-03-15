package com.orcchg.yandexcontest.stocklist.demo.convert

import androidx.annotation.DrawableRes
import com.orcchg.yandexcontest.stocklist.demo.R
import com.orcchg.yandexcontest.util.ResourceSupplier
import javax.inject.Inject

class FakeLogoResByTickerSupplier @Inject constructor() : ResourceSupplier {

    @DrawableRes
    override fun getResIdByKey(key: String): Int =
        when (key) {
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
