package com.orcchg.yandexcontest.stockdetails.data.api

import com.orcchg.yandexcontest.coredi.Api

interface StockDetailsDataApi : Api {
    fun stockDetailsRepository(): StockDetailsRepository
}
