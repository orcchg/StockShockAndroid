package com.orcchg.yandexcontest.search.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.SingleUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.search.domain.SearchRepository
import io.reactivex.Single
import javax.inject.Inject

class GetPopularSearchUseCase @Inject constructor(
    private val repository: SearchRepository,
    schedulersFactory: SchedulersFactory
) : SingleUseCase<Collection<String>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Single<Collection<String>> =
        repository.popularSearch()
}
