package com.orcchg.yandexcontest.stocklist.data.api

import com.orcchg.yandexcontest.coredi.Api

interface StockListDataApi : Api {
    fun realTimeStocksRepository(): RealTimeStocksRepository

    fun stockListRepository(): StockListRepository
}
