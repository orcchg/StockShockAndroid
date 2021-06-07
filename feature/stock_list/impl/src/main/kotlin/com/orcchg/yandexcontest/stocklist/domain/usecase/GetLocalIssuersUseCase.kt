package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.SingleUseCase
import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLocalIssuersUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : SingleUseCase<List<Issuer>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Single<List<Issuer>> =
        repository.localIssuers()
}
