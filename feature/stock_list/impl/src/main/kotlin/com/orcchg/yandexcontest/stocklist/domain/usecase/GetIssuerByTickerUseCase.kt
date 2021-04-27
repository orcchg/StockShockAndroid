package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.processMaybe
import com.orcchg.yandexcontest.base.usecase.MaybeUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetIssuerByTickerUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : MaybeUseCase<Issuer>(schedulersFactory) {

    override fun sourceImpl(params: Params): Maybe<Issuer> =
        params.processMaybe(PARAM_TICKER, repository::issuer)

    companion object {
        const val PARAM_TICKER = "ticker"
    }
}
