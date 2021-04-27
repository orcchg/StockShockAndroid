package com.orcchg.yandexcontest.stocklist.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.ObservableUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavouriteIssuersChangedUseCase @Inject constructor(
    private val repository: StockListRepository,
    schedulersFactory: SchedulersFactory
) : ObservableUseCase<IssuerFavourite>(schedulersFactory) {

    override fun sourceImpl(params: Params): Observable<IssuerFavourite> =
        repository.favouriteIssuersChanged
}
