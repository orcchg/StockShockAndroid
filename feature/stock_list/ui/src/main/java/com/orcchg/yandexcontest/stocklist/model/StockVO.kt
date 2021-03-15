package com.orcchg.yandexcontest.stocklist.model

import androidx.annotation.DrawableRes
import com.orcchg.yandexcontest.util.goodHashCode

data class StockVO(
    val name: String,
    val price: String,
    val priceDailyChange: String,
    val ticker: String,
    @DrawableRes val logoResId: Int = 0,
    val logoUrl: String? = null,
    val isFavourite: Boolean
) {

    fun id(): Long = ticker.goodHashCode()
}
