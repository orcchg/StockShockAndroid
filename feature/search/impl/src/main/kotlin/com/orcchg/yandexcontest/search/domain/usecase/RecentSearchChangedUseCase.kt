package com.orcchg.yandexcontest.search.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.ObservableUseCase
import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.search.domain.SearchRepository
import io.reactivex.Observable
import javax.inject.Inject

class RecentSearchChangedUseCase @Inject constructor(
    private val repository: SearchRepository,
    schedulersFactory: SchedulersFactory
) : ObservableUseCase<Boolean>(schedulersFactory) {

    override fun sourceImpl(params: Params): Observable<Boolean> =
        repository.recentSearchesChanged
}
