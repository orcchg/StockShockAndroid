package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.ObservableUseCase
import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import io.reactivex.Observable
import javax.inject.Inject

class MissingQuotesUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : ObservableUseCase<Collection<Quote>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Observable<Collection<Quote>> =
        repository.missingQuotes
}
