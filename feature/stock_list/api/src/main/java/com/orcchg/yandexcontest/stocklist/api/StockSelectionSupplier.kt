package com.orcchg.yandexcontest.stocklist.api

import com.orcchg.yandexcontest.coremodel.StockSelection

fun interface StockSelectionSupplier {

    fun stockSelection(ticker: String): StockSelection
}
