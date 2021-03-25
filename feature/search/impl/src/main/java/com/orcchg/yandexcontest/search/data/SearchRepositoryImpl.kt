package com.orcchg.yandexcontest.search.data

import com.orcchg.yandexcontest.search.domain.SearchRepository
import com.orcchg.yandexcontest.util.algorithm.InMemorySearchManager
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {

    private val recentSearches = mutableSetOf<String>()
    private val _recentSearchesChanged = BehaviorSubject.create<Boolean>()
    override val recentSearchesChanged: Observable<Boolean> = _recentSearchesChanged.hide()

    override fun popularSearch(): Single<Collection<String>> =
        Single.just(listOf(
            "Apple", "Amazon", "Google", "Tesla", "Microsoft",
            "First Solar", "Alibaba", "Facebook", "Mastercard"
        ))

    override fun recentSearch(): Single<Collection<String>> = Single.just(recentSearches)

    override fun addRecentSearch(search: String): Completable =
        Completable.fromCallable {
            Timber.e("SEARCH: $search")
            if (search.length > 4) {
                val changed = InMemorySearchManager.findByPrefix(search)
                    .map(recentSearches::add)
                    .all { true }

                if (changed) {
                    Timber.v("Add recent search item: $search")
                }
                _recentSearchesChanged.onNext(changed)
            }
        }
}
