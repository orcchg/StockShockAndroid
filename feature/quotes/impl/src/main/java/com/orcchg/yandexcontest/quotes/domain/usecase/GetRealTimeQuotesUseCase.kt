package com.orcchg.yandexcontest.quotes.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.FlowableUseCase
import com.orcchg.yandexcontest.coremodel.Quote
import com.orcchg.yandexcontest.quotes.domain.RealTimeStocksRepository
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import io.reactivex.Flowable
import javax.inject.Inject

class GetRealTimeQuotesUseCase @Inject constructor(
    private val repository: RealTimeStocksRepository,
    schedulersFactory: SchedulersFactory
) : FlowableUseCase<List<Quote>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Flowable<List<Quote>> =
        repository.realTimeQuotes()
}
