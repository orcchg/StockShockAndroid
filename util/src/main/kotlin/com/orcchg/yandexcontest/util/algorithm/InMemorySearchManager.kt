package com.orcchg.yandexcontest.util.algorithm

object InMemorySearchManager {

    private val manager = SearchByPrefixManager(emptySet(), ignoreCase = true)

    fun addWord(word: String) {
        manager.addWord(word)
    }

    fun findByPrefix(prefix: String): Collection<String> = manager.findByPrefix(prefix)
}
