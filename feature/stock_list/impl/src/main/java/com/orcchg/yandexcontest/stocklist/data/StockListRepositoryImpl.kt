package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import io.reactivex.Single
import javax.inject.Inject

class StockListRepositoryImpl @Inject constructor() : StockListRepository {

    override fun stocks(): Single<List<Stock>> = Single.just(emptyList())
}
