package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.processSingle
import com.orcchg.yandexcontest.base.usecase.SingleUseCase
import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import io.reactivex.Single
import javax.inject.Inject

class GetQuoteByTickerUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : SingleUseCase<Quote>(schedulersFactory) {

    override fun sourceImpl(params: Params): Single<Quote> =
        params.processSingle(PARAM_TICKER, repository::quote)

    companion object {
        const val PARAM_TICKER = "ticker"
    }
}
