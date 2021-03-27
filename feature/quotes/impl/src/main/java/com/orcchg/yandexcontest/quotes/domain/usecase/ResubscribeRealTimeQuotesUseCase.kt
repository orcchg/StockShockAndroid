package com.orcchg.yandexcontest.quotes.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.CompletableUseCase
import com.orcchg.yandexcontest.quotes.domain.RealTimeStocksRepository
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import io.reactivex.Completable
import javax.inject.Inject

class ResubscribeRealTimeQuotesUseCase @Inject constructor(
    private val repository: RealTimeStocksRepository,
    schedulersFactory: SchedulersFactory
) : CompletableUseCase(schedulersFactory) {

    override fun sourceImpl(params: Params): Completable = repository.invalidate()
}
