package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.processCompletable
import com.orcchg.yandexcontest.base.usecase.CompletableUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import io.reactivex.Completable
import javax.inject.Inject

class InvalidateCacheUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : CompletableUseCase(schedulersFactory) {

    override fun sourceImpl(params: Params): Completable =
        params.processCompletable(PARAM_STOCK_SELECTION, repository::invalidateCache)

    companion object {
        const val PARAM_STOCK_SELECTION = "stock_selection"
    }
}