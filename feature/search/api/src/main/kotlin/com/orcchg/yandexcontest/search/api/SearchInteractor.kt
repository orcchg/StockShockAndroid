package com.orcchg.yandexcontest.search.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface SearchInteractor {

    val recentSearchesChanged: Observable<Boolean>

    fun popularSearch(): Single<Collection<String>>

    fun recentSearch(): Single<Collection<String>>

    fun addRecentSearch(item: String): Completable
}
