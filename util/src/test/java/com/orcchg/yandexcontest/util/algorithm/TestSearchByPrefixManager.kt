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

        Assert.assertTrue(smallSearchManager.contains("YNDX"))
        Assert.assertTrue(smallSearchManager.contains("AAPL"))
        Assert.assertTrue(smallSearchManager.contains("GOOGL"))
        Assert.assertTrue(smallSearchManager.contains("AMZN"))
        Assert.assertTrue(smallSearchManager.contains("BAC"))
        Assert.assertTrue(smallSearchManager.contains("MSFT"))
        Assert.assertTrue(smallSearchManager.contains("TSLA"))
        Assert.assertTrue(smallSearchManager.contains("MA"))
        Assert.assertTrue(smallSearchManager.contains("FB"))
        Assert.assertTrue(smallSearchManager.contains("GAZP"))
        Assert.assertTrue(smallSearchManager.contains("ROSN"))
        Assert.assertTrue(smallSearchManager.contains("GMKN"))
        Assert.assertTrue(smallSearchManager.contains("SBER"))
        Assert.assertTrue(smallSearchManager.contains("MAIL"))
        Assert.assertTrue(smallSearchManager.contains("APPN"))
        Assert.assertTrue(smallSearchManager.contains("APPF"))
        Assert.assertTrue(smallSearchManager.contains("APPI"))

        Assert.assertTrue(searchManager.contains("Yandex, LLC"))
        Assert.assertTrue(searchManager.contains("Apple Inc."))
        Assert.assertTrue(searchManager.contains("Alphabet Class A"))
        Assert.assertTrue(searchManager.contains("Amazon.com"))
        Assert.assertTrue(searchManager.contains("Bank of America Corp"))
        Assert.assertTrue(searchManager.contains("Microsoft Corporation"))
        Assert.assertTrue(searchManager.contains("Tesla Motors"))
        Assert.assertTrue(searchManager.contains("Mastercard"))
        Assert.assertTrue(searchManager.contains("Facebook"))
        Assert.assertTrue(searchManager.contains("Gazprom"))
        Assert.assertTrue(searchManager.contains("Rosneft"))
        Assert.assertTrue(searchManager.contains("GMK Nor Nickel"))
        Assert.assertTrue(searchManager.contains("Sberbank"))
        Assert.assertTrue(searchManager.contains("Mail.ru Group"))
        Assert.assertTrue(searchManager.contains("Appian Corp."))
        Assert.assertTrue(searchManager.contains("Appfolio Inc."))
        Assert.assertTrue(searchManager.contains("Appi Inc."))
    }

    @Test
    fun `test contains() should return TRUE if query is present in data structure Ignore case`() {
        Assert.assertTrue(searchManagerIgnoreCase.contains("YNDX"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("AAPL"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("GOOGL"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("AMZN"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("BAC"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("MSFT"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("TSLA"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("MA"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("FB"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("GAZP"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("ROSN"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("GMKN"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("SBER"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("MAIL"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("APPN"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("APPF"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("APPI"))

        Assert.assertTrue(searchManagerIgnoreCase.contains("yndx"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("aapl"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("googl"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("amzn"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("bac"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("msft"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("tsla"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("ma"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("fb"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("gazp"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("rosn"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("gmkn"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("sber"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("mail"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("appn"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("appf"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("appi"))

        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("YNDX"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("AAPL"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("GOOGL"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("AMZN"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("BAC"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("MSFT"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("TSLA"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("MA"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("FB"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("GAZP"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("ROSN"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("GMKN"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("SBER"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("MAIL"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("APPN"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("APPF"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("APPI"))

        Assert.assertTrue(searchManagerIgnoreCase.contains("Yandex, LLC"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Apple Inc."))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Alphabet Class A"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Amazon.com"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Bank of America Corp"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Microsoft Corporation"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Tesla Motors"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Mastercard"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Facebook"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Gazprom"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Rosneft"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("GMK Nor Nickel"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Sberbank"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Mail.ru Group"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Appian Corp."))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Appfolio Inc."))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Appi Inc."))

        Assert.assertTrue(searchManagerIgnoreCase.contains("YANDEX, LLC"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("apple inc."))
        Assert.assertTrue(searchManagerIgnoreCase.contains("ALPHABET CLASS A"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("amazon.com"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("BANK OF AMERICA CORP"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("microsoft corporation"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("TESLA MOTORS"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("mastercard"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("FACEBOOK"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("gazprom"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("ROSNEFT"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("gmk nor nickel"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("SBERBANK"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("mail.ru group"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("APPIAN CORP."))
        Assert.assertTrue(searchManagerIgnoreCase.contains("appfolio inc."))
        Assert.assertTrue(searchManagerIgnoreCase.contains("APPI INC."))
    }

    @Test
    fun `test contains() should return FALSE if query is absent in data structure`() {
        Assert.assertFalse(searchManager.contains("GOOG"))
        Assert.assertFalse(searchManager.contains("LYFT"))
        Assert.assertFalse(searchManager.contains("DODO"))
        Assert.assertFalse(searchManager.contains("CHMF"))
        Assert.assertFalse(searchManager.contains("URKA"))

        Assert.assertFalse(searchManager.contains("yndx"))
        Assert.assertFalse(searchManager.contains("aapl"))
        Assert.assertFalse(searchManager.contains("googl"))
        Assert.assertFalse(searchManager.contains("amzn"))
        Assert.assertFalse(searchManager.contains("bac"))
        Assert.assertFalse(searchManager.contains("msft"))
        Assert.assertFalse(searchManager.contains("tsla"))
        Assert.assertFalse(searchManager.contains("ma"))
        Assert.assertFalse(searchManager.contains("fb"))
        Assert.assertFalse(searchManager.contains("gazp"))
        Assert.assertFalse(searchManager.contains("rosn"))
        Assert.assertFalse(searchManager.contains("gmkn"))
        Assert.assertFalse(searchManager.contains("sber"))
        Assert.assertFalse(searchManager.contains("mail"))
        Assert.assertFalse(searchManager.contains("appn"))
        Assert.assertFalse(searchManager.contains("appf"))
        Assert.assertFalse(searchManager.contains("appi"))

        Assert.assertFalse(smallSearchManager.contains("Yandex, LLC"))
        Assert.assertFalse(smallSearchManager.contains("Apple Inc."))
        Assert.assertFalse(smallSearchManager.contains("Alphabet Class A"))
        Assert.assertFalse(smallSearchManager.contains("Amazon.com"))
        Assert.assertFalse(smallSearchManager.contains("Bank of America Corp"))
        Assert.assertFalse(smallSearchManager.contains("Microsoft Corporation"))
        Assert.assertFalse(smallSearchManager.contains("Tesla Motors"))
        Assert.assertFalse(smallSearchManager.contains("Mastercard"))
        Assert.assertFalse(smallSearchManager.contains("Facebook"))
        Assert.assertFalse(smallSearchManager.contains("Gazprom"))
        Assert.assertFalse(smallSearchManager.contains("Rosneft"))
        Assert.assertFalse(smallSearchManager.contains("GMK Nor Nickel"))
        Assert.assertFalse(smallSearchManager.contains("Sberbank"))
        Assert.assertFalse(smallSearchManager.contains("Mail.ru Group"))
        Assert.assertFalse(smallSearchManager.contains("Appian Corp."))
        Assert.assertFalse(smallSearchManager.contains("Appfolio Inc."))
        Assert.assertFalse(smallSearchManager.contains("Appi Inc."))

        Assert.assertFalse(searchManager.contains("YANDEX, LLC"))
        Assert.assertFalse(searchManager.contains("apple inc."))
        Assert.assertFalse(searchManager.contains("ALPHABET CLASS A"))
        Assert.assertFalse(searchManager.contains("amazon.com"))
        Assert.assertFalse(searchManager.contains("BANK OF AMERICA CORP"))
        Assert.assertFalse(searchManager.contains("microsoft corporation"))
        Assert.assertFalse(searchManager.contains("TESLA MOTORS"))
        Assert.assertFalse(searchManager.contains("mastercard"))
        Assert.assertFalse(searchManager.contains("FACEBOOK"))
        Assert.assertFalse(searchManager.contains("gazprom"))
        Assert.assertFalse(searchManager.contains("ROSNEFT"))
        Assert.assertFalse(searchManager.contains("gmk nor nickel"))
        Assert.assertFalse(searchManager.contains("SBERBANK"))
        Assert.assertFalse(searchManager.contains("mail.ru group"))
        Assert.assertFalse(searchManager.contains("APPIAN CORP."))
        Assert.assertFalse(searchManager.contains("appfolio inc."))
        Assert.assertFalse(searchManager.contains("APPI INC."))
    }

    @Test
    fun `test contains() should return FALSE if query is absent in data structure Ignore case`() {
        Assert.assertFalse(searchManagerIgnoreCase.contains("GOOG"))
        Assert.assertFalse(searchManagerIgnoreCase.contains("LYFT"))
        Assert.assertFalse(searchManagerIgnoreCase.contains("DODO"))
        Assert.assertFalse(searchManagerIgnoreCase.contains("CHMF"))
        Assert.assertFalse(searchManagerIgnoreCase.contains("URKA"))

        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Yandex, LLC"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Apple Inc."))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Alphabet Class A"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Amazon.com"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Bank of America Corp"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Microsoft Corporation"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Tesla Motors"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Mastercard"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Facebook"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Gazprom"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Rosneft"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("GMK Nor Nickel"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Sberbank"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Mail.ru Group"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Appian Corp."))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Appfolio Inc."))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("Appi Inc."))
    }

    @Test
    fun `test findByPrefix() should return all words in data structure that start with a given prefix`() {
        assertThat(searchManager.findByPrefix("APP"),
            allOf(
                iterableWithSize(3),
                containsInAnyOrder("APPI", "APPF", "APPN")
            )
        )

        assertThat(searchManager.findByPrefix("App"),
            allOf(
                iterableWithSize(4),
                containsInAnyOrder("Appian Corp.", "Appi Inc.", "Apple Inc.", "Appfolio Inc.")
            )
        )

        assertThat(searchManager.findByPrefix("G"),
            allOf(
                iterableWithSize(5),
                containsInAnyOrder("GAZP", "GOOGL", "GMKN", "Gazprom", "GMK Nor Nickel")
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

    @Test
    fun `test findByPrefix() should return all words in data structure that start with a given prefix Ignore case`() {
        assertThat(searchManagerIgnoreCase.findByPrefix("APP"),
            allOf(
                iterableWithSize(7),
                containsInAnyOrder("APPI", "APPF", "APPN", "Appian Corp.", "Appi Inc.", "Apple Inc.", "Appfolio Inc.")
            )
        )

        assertThat(searchManagerIgnoreCase.findByPrefix("App"),
            allOf(
                iterableWithSize(7),
                containsInAnyOrder("Appian Corp.", "Appi Inc.", "Apple Inc.", "Appfolio Inc.", "APPI", "APPF", "APPN")
            )
        )

        assertThat(searchManagerIgnoreCase.findByPrefix("G"),
            allOf(
                iterableWithSize(5),
                containsInAnyOrder("GAZP", "GOOGL", "GMKN", "Gazprom", "GMK Nor Nickel")
            )
        )

        assertThat(searchManagerIgnoreCase.findByPrefix("MA"),
            allOf(
                iterableWithSize(4),
                containsInAnyOrder("MA", "MAIL", "Mail.ru Group", "Mastercard")
            )
        )

        assertThat(searchManagerIgnoreCase.findByPrefix("SB"),
            allOf(
                iterableWithSize(2),
                containsInAnyOrder("Sberbank", "SBER")
            )
        )

        assertThat(searchManagerIgnoreCase.findByPrefix("FIVE"), empty())
        assertThat(searchManagerIgnoreCase.findByPrefix("X"), empty())
    }

    companion object {
        private lateinit var searchManager: SearchByPrefixManager
        private lateinit var smallSearchManager: SearchByPrefixManager
        private lateinit var searchManagerIgnoreCase: SearchByPrefixManager
        private lateinit var smallSearchManagerIgnoreCase: SearchByPrefixManager

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            searchManager = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI",
                    "Yandex, LLC", "Apple Inc.", "Alphabet Class A", "Amazon.com",
                    "Bank of America Corp", "Microsoft Corporation", "Tesla Motors",
                    "Mastercard", "Facebook", "Gazprom", "Rosneft", "GMK Nor Nickel",
                    "Sberbank", "Mail.ru Group", "Appian Corp.", "Appfolio Inc.",
                    "Appi Inc."
                ),
                ignoreCase = false
            )
            
            smallSearchManager = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI"
                ),
                ignoreCase = false
            )

            searchManagerIgnoreCase = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI",
                    "Yandex, LLC", "Apple Inc.", "Alphabet Class A", "Amazon.com",
                    "Bank of America Corp", "Microsoft Corporation", "Tesla Motors",
                    "Mastercard", "Facebook", "Gazprom", "Rosneft", "GMK Nor Nickel",
                    "Sberbank", "Mail.ru Group", "Appian Corp.", "Appfolio Inc.",
                    "Appi Inc."
                ),
                ignoreCase = true
            )

            smallSearchManagerIgnoreCase = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI"
                ),
                ignoreCase = true
            )
        }
    }
}
