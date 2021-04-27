package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.CompletableUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import io.reactivex.Completable
import javax.inject.Inject

class SetIssuerFavouriteUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : CompletableUseCase(schedulersFactory) {

    override fun sourceImpl(params: Params): Completable =
        repository.setIssuerFavourite(
            ticker = params.require(PARAM_TICKER),
            isFavourite = params.require(PARAM_IS_FAVOURITE)
        )

    companion object {
        const val PARAM_TICKER = "ticker"
        const val PARAM_IS_FAVOURITE = "favourite"
    }
}
