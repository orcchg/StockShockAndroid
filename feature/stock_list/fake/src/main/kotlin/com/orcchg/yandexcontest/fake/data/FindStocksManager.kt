package com.orcchg.yandexcontest.fake.data

import com.orcchg.yandexcontest.util.algorithm.SearchByPrefixManager
import javax.inject.Inject

class FindStocksManager @Inject constructor() {

    private val searchManager: SearchByPrefixManager

    init {
        // adding NAME and TICKER
        val dictionary = fakeIssuers.map { it.name }.plus(fakeIssuers.map { it.ticker })
        searchManager = SearchByPrefixManager(dictionary, ignoreCase = true)
    }

    fun contains(word: String): Boolean = searchManager.contains(word)
    fun findByPrefix(prefix: String): Collection<String> =
        searchManager.findByPrefix(prefix).sorted()
}
