package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.SingleUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import io.reactivex.Single
import javax.inject.Inject

class GetStocksUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : SingleUseCase<List<Stock>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Single<List<Stock>> =
        repository.stocks()
}
