package com.orcchg.yandexcontest.search.data

import com.orcchg.yandexcontest.search.domain.SearchRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor() : SearchRepository {

    private val recentSearches = mutableListOf<String>()

    override fun popularSearch(): Single<List<String>> =
        Single.just(listOf(
            "Apple", "Amazon", "Google", "Tesla", "Microsoft",
            "First Solar", "Alibaba", "Facebook", "Mastercard"
        ))

    override fun recentSearch(): Single<List<String>> = Single.just(recentSearches)

    override fun addRecentSearch(search: String): Completable =
        Completable.fromCallable { recentSearches.add(search) }
}
