package com.orcchg.yandexcontest.stocklist.data.api

interface StockListDataApi {
    fun realTimeStocksRepository(): RealTimeStocksRepository

    fun stockListRepository(): StockListRepository
}
