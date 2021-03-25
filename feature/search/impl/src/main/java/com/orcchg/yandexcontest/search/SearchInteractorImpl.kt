package com.orcchg.yandexcontest.search

import com.orcchg.yandexcontest.search.api.SearchInteractor
import com.orcchg.yandexcontest.search.domain.usecase.AddRecentSearchUseCase
import com.orcchg.yandexcontest.search.domain.usecase.GetPopularSearchUseCase
import com.orcchg.yandexcontest.search.domain.usecase.GetRecentSearchUseCase
import com.orcchg.yandexcontest.search.domain.usecase.RecentSearchChangedUseCase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class SearchInteractorImpl @Inject constructor(
    private val addRecentSearchUseCase: AddRecentSearchUseCase,
    private val getPopularSearchUseCase: GetPopularSearchUseCase,
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
    recentSearchChangedUseCase: RecentSearchChangedUseCase
) : SearchInteractor {

    override val recentSearchesChanged: Observable<Boolean> =
        recentSearchChangedUseCase.source()

    override fun popularSearch(): Single<Collection<String>> =
        getPopularSearchUseCase.source()

    override fun recentSearch(): Single<Collection<String>> =
        getRecentSearchUseCase.source()

    override fun addRecentSearch(item: String): Completable =
        addRecentSearchUseCase.source { AddRecentSearchUseCase.PARAM_SEARCH of item }
}
