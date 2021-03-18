package com.orcchg.yandexcontest.util.algorithm

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class TestSearchByPrefixManager {

    @Test
    fun `test contains() should return TRUE if query is present in data structure`() {
        Assert.assertTrue(searchManager.contains("YNDX"))
        Assert.assertTrue(searchManager.contains("AAPL"))
        Assert.assertTrue(searchManager.contains("GOOGL"))
        Assert.assertTrue(searchManager.contains("AMZN"))
        Assert.assertTrue(searchManager.contains("BAC"))
        Assert.assertTrue(searchManager.contains("MSFT"))
        Assert.assertTrue(searchManager.contains("TSLA"))
        Assert.assertTrue(searchManager.contains("MA"))
        Assert.assertTrue(searchManager.contains("FB"))
        Assert.assertTrue(searchManager.contains("GAZP"))
        Assert.assertTrue(searchManager.contains("ROSN"))
        Assert.assertTrue(searchManager.contains("GMKN"))
        Assert.assertTrue(searchManager.contains("SBER"))
        Assert.assertTrue(searchManager.contains("MAIL"))
        Assert.assertTrue(searchManager.contains("APPN"))
        Assert.assertTrue(searchManager.contains("APPF"))
        Assert.assertTrue(searchManager.contains("APPI"))
    }

    @Test
    fun `test contains() should return FALSE if query is absent in data structure`() {
        Assert.assertFalse(searchManager.contains("GOOG"))
        Assert.assertFalse(searchManager.contains("LYFT"))
        Assert.assertFalse(searchManager.contains("DODO"))
        Assert.assertFalse(searchManager.contains("CHMF"))
        Assert.assertFalse(searchManager.contains("URKA"))
    }

    @Test
    fun `test findByPrefix() should return all words in data structure that start with a given prefix`() {
        assertThat(searchManager.findByPrefix("APP"),
            allOf(
                iterableWithSize(3),
                containsInAnyOrder("APPI", "APPF", "APPN")
            )
        )

        assertThat(searchManager.findByPrefix("G"),
            allOf(
                iterableWithSize(3),
                containsInAnyOrder("GAZP", "GOOGL", "GMKN")
            )
        )

        assertThat(searchManager.findByPrefix("MA"),
            allOf(
                iterableWithSize(2),
                containsInAnyOrder("MA", "MAIL")
            )
        )

        assertThat(searchManager.findByPrefix("SB"),
            allOf(
                iterableWithSize(1),
                containsInAnyOrder("SBER")
            )
        )

        assertThat(searchManager.findByPrefix("FIVE"), empty())
        assertThat(searchManager.findByPrefix("X"), empty())
    }

    companion object {
        private lateinit var searchManager: SearchByPrefixManager

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            searchManager = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI"
                ),
                ignoreCase = false
            )
        }
    }
}
