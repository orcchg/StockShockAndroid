package com.orcchg.yandexcontest.stocklist.domain

import com.orcchg.yandexcontest.stocklist.api.model.Stock
import io.reactivex.Single

interface StockListRepository {

    fun stocks(): Single<List<Stock>>
}
