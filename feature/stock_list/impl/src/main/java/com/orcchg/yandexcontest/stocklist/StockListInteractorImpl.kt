package com.orcchg.yandexcontest.stocklist

import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import io.reactivex.Single
import javax.inject.Inject

class StockListInteractorImpl @Inject constructor() : StockListInteractor {

    override fun stocks(): Single<List<Stock>> = Single.just(emptyList())
}
