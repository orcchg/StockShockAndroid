package com.orcchg.yandexcontest.fake.data

import com.orcchg.yandexcontest.util.algorithm.SearchByPrefixManager
import javax.inject.Inject

class FindStocksManager @Inject constructor() {

    private val searchManager = SearchByPrefixManager(
        dictionary = listOf(
            "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
            "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI"
        )
    )

    fun contains(word: String): Boolean = searchManager.contains(word)
    fun findByPrefix(prefix: String): Collection<String> = searchManager.findByPrefix(prefix)
}
