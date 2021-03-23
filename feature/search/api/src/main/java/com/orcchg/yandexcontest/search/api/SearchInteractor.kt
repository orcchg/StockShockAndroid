package com.orcchg.yandexcontest.search.api

import io.reactivex.Completable
import io.reactivex.Single

interface SearchInteractor {

    fun popularSearch(): Single<List<String>>

    fun recentSearch(): Single<List<String>>

    fun addRecentSearch(item: String): Completable
}
