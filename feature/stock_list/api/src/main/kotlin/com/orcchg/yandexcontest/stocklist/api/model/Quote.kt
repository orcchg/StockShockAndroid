package com.orcchg.yandexcontest.stocklist.api.model

import com.orcchg.yandexcontest.coremodel.Money

data class Quote(
    val ticker: String,
    /**
     * Current price at quote retrieval.
     */
    val currentPrice: Money = Money.ZERO,
    /**
     * Close price of the previous day.
     */
    val prevClosePrice: Money = Money.ZERO,
    /**
     * Highest price of the day.
     */
    val maxPrice: Money = Money.ZERO,
    /**
     * Lowest price of the day.
     */
    val minPrice: Money = Money.ZERO,
    /**
     * Open price of the day.
     */
    val openPrice: Money = Money.ZERO
) {

    val priceDayChange = currentPrice - prevClosePrice
}
