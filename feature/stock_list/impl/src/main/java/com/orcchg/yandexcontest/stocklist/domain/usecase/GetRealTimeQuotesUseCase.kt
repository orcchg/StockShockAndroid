package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.FlowableUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.coremodel.Quote
import com.orcchg.yandexcontest.stocklist.domain.RealTimeStocksRepository
import io.reactivex.Flowable
import javax.inject.Inject

@Deprecated("quotes")
class GetRealTimeQuotesUseCase @Inject constructor(
    private val repository: RealTimeStocksRepository,
    schedulersFactory: SchedulersFactory
) : FlowableUseCase<List<Quote>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Flowable<List<Quote>> =
        repository.realTimeQuotes()
}
