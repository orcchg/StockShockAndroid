package com.orcchg.yandexcontest.stocklist.api

import com.orcchg.yandexcontest.stocklist.api.model.Stock
import io.reactivex.Single

interface StockListInteractor {

    fun stocks(): Single<List<Stock>>
}
