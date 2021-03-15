package com.orcchg.yandexcontest.stocklist.api.model

import com.orcchg.yandexcontest.coremodel.Money

data class Quote(
    val currentPrice: Money = Money.ZERO,
    val prevClosePrice: Money = Money.ZERO
)
