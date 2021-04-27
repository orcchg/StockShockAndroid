package com.orcchg.yandexcontest.search.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface SearchRepository {

    val recentSearchesChanged: Observable<Boolean>

    fun popularSearch(): Single<Collection<String>>

    fun recentSearch(): Single<Collection<String>>

    fun addRecentSearch(search: String): Completable
}
