package com.orcchg.yandexcontest.search.domain

import io.reactivex.Completable
import io.reactivex.Single

interface SearchRepository {

    fun popularSearch(): Single<List<String>>

    fun recentSearch(): Single<List<String>>

    fun addRecentSearch(search: String): Completable
}
