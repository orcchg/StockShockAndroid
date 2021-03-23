package com.orcchg.yandexcontest.search.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.processCompletable
import com.orcchg.yandexcontest.base.usecase.CompletableUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.search.domain.SearchRepository
import io.reactivex.Completable
import javax.inject.Inject

class AddRecentSearchUseCase @Inject constructor(
    private val repository: SearchRepository,
    schedulersFactory: SchedulersFactory
) : CompletableUseCase(schedulersFactory) {

    override fun sourceImpl(params: Params): Completable =
        params.processCompletable(PARAM_SEARCH, repository::addRecentSearch)

    companion object {
        const val PARAM_SEARCH = "search"
    }
}
