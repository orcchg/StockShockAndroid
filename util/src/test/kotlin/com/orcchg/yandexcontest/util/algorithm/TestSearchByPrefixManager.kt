package com.orcchg.yandexcontest.util.algorithm

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.iterableWithSize
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class TestSearchByPrefixManager {

    @Test
    fun `test size() of all search managers`() {
        assertThat(searchManager.size(), Is(equalTo(38)))
        assertThat(smallSearchManager.size(), Is(equalTo(19)))
        assertThat(searchManagerIgnoreCase.size(), Is(equalTo(38)))
        assertThat(smallSearchManagerIgnoreCase.size(), Is(equalTo(19)))
    }

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
        Assert.assertTrue(searchManager.contains("VTB24"))
        Assert.assertTrue(searchManager.contains("3M"))

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
        Assert.assertTrue(smallSearchManager.contains("VTB24"))
        Assert.assertTrue(smallSearchManager.contains("3M"))

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
        Assert.assertTrue(searchManager.contains("VneshTorgBank 24"))
        Assert.assertTrue(searchManager.contains("3M&ms"))
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
        Assert.assertTrue(searchManagerIgnoreCase.contains("3M"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("VTB24"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("3m"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("Vtb24"))

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
        Assert.assertTrue(searchManagerIgnoreCase.contains("3M&ms"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("3m&ms"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("3M&MS"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("3M&mS"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("VneshTorgBank 24"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("vneshtorgBank 24"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("vneshtorgbank 24"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("VNeshTORGBank 24"))
        Assert.assertTrue(searchManagerIgnoreCase.contains("VNESHTorgBANK 24"))

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
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("VTB24"))
        Assert.assertTrue(smallSearchManagerIgnoreCase.contains("3M"))

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
        Assert.assertFalse(searchManager.contains("3M&MS"))
        Assert.assertFalse(searchManager.contains("VTB"))

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
        Assert.assertFalse(searchManager.contains("3m&Ms"))
        Assert.assertFalse(searchManager.contains("3M&Ms"))
        Assert.assertFalse(searchManager.contains("3m"))
        Assert.assertFalse(searchManager.contains("Vtb"))
        Assert.assertFalse(searchManager.contains("VTB 24"))
        Assert.assertFalse(searchManager.contains("VneshTorgBank"))
        Assert.assertFalse(searchManager.contains("VneshtorgBank 24"))
        Assert.assertFalse(searchManager.contains("vneshtorgbank"))
        Assert.assertFalse(searchManager.contains("vneshtorgbank 24"))
        Assert.assertFalse(searchManager.contains("VnesHTorgBAnk 24"))

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
        Assert.assertFalse(smallSearchManager.contains("VneshTorgBank 24"))
        Assert.assertFalse(smallSearchManager.contains("3M&ms"))

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
        Assert.assertFalse(searchManagerIgnoreCase.contains("3M&M"))
        Assert.assertFalse(searchManagerIgnoreCase.contains("VTB"))

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
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("3m&Ms"))
        Assert.assertFalse(smallSearchManagerIgnoreCase.contains("vneshtorgbank 24"))
    }

    @Test
    fun `test findByPrefix() should return all words in data structure that start with a given prefix`() {
        assertThat(
            searchManager.findByPrefix("APP"),
            allOf(
                iterableWithSize(3),
                containsInAnyOrder("APPI", "APPF", "APPN")
            )
        )

        assertThat(
            searchManager.findByPrefix("App"),
            allOf(
                iterableWithSize(4),
                containsInAnyOrder("Appian Corp.", "Appi Inc.", "Apple Inc.", "Appfolio Inc.")
            )
        )

        assertThat(
            searchManager.findByPrefix("G"),
            allOf(
                iterableWithSize(5),
                containsInAnyOrder("GAZP", "GOOGL", "GMKN", "Gazprom", "GMK Nor Nickel")
            )
        )

        assertThat(
            searchManager.findByPrefix("MA"),
            allOf(
                iterableWithSize(2),
                containsInAnyOrder("MA", "MAIL")
            )
        )

        assertThat(
            searchManager.findByPrefix("SB"),
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
        assertThat(
            searchManagerIgnoreCase.findByPrefix("APP"),
            allOf(
                iterableWithSize(7),
                containsInAnyOrder(
                    "APPI",
                    "APPF",
                    "APPN",
                    "Appian Corp.",
                    "Appi Inc.",
                    "Apple Inc.",
                    "Appfolio Inc."
                )
            )
        )

        assertThat(
            searchManagerIgnoreCase.findByPrefix("App"),
            allOf(
                iterableWithSize(7),
                containsInAnyOrder(
                    "Appian Corp.",
                    "Appi Inc.",
                    "Apple Inc.",
                    "Appfolio Inc.",
                    "APPI",
                    "APPF",
                    "APPN"
                )
            )
        )

        assertThat(
            searchManagerIgnoreCase.findByPrefix("G"),
            allOf(
                iterableWithSize(5),
                containsInAnyOrder("GAZP", "GOOGL", "GMKN", "Gazprom", "GMK Nor Nickel")
            )
        )

        assertThat(
            searchManagerIgnoreCase.findByPrefix("MA"),
            allOf(
                iterableWithSize(4),
                containsInAnyOrder("MA", "MAIL", "Mail.ru Group", "Mastercard")
            )
        )

        assertThat(
            searchManagerIgnoreCase.findByPrefix("SB"),
            allOf(
                iterableWithSize(2),
                containsInAnyOrder("Sberbank", "SBER")
            )
        )

        assertThat(searchManagerIgnoreCase.findByPrefix("FIVE"), empty())
        assertThat(searchManagerIgnoreCase.findByPrefix("X"), empty())
    }

    @Test
    fun `test addWord() and check contains() returns TRUE`() {
        val sm = SearchByPrefixManager(
            dictionary = listOf(
                "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI"
            ),
            ignoreCase = false
        )

        Assert.assertTrue(sm.contains("YNDX"))
        Assert.assertTrue(sm.contains("AAPL"))
        Assert.assertTrue(sm.contains("GOOGL"))
        Assert.assertTrue(sm.contains("AMZN"))
        Assert.assertTrue(sm.contains("BAC"))
        Assert.assertTrue(sm.contains("MSFT"))
        Assert.assertTrue(sm.contains("TSLA"))
        Assert.assertTrue(sm.contains("MA"))
        Assert.assertTrue(sm.contains("FB"))
        Assert.assertTrue(sm.contains("GAZP"))
        Assert.assertTrue(sm.contains("ROSN"))
        Assert.assertTrue(sm.contains("GMKN"))
        Assert.assertTrue(sm.contains("SBER"))
        Assert.assertTrue(sm.contains("MAIL"))
        Assert.assertTrue(sm.contains("APPN"))
        Assert.assertTrue(sm.contains("APPF"))
        Assert.assertTrue(sm.contains("APPI"))

        assertThat(sm.size(), Is(equalTo(17)))

        with(sm) {
            addWord("APPLE")
            addWord("SBERBANK")
            addWord("MASTERCARD")
            addWord("ROSNEFT")
            addWord("GAZPROM")
        }

        assertThat(sm.size(), Is(equalTo(22)))

        Assert.assertTrue(sm.contains("APPLE"))
        Assert.assertTrue(sm.contains("SBERBANK"))
        Assert.assertTrue(sm.contains("MASTERCARD"))
        Assert.assertTrue(sm.contains("ROSNEFT"))
        Assert.assertTrue(sm.contains("GAZPROM"))

        with(sm) {
            addWord("SBERCAS")
            addWord("SBE")
            addWord("ROSTEL")
            addWord("ROS")
            addWord("ROSNANO")
            addWord("GAZ")
            addWord("GAZGOLDER")
        }

        assertThat(sm.size(), Is(equalTo(29)))

        Assert.assertTrue(sm.contains("SBERCAS"))
        Assert.assertTrue(sm.contains("SBE"))
        Assert.assertTrue(sm.contains("ROSTEL"))
        Assert.assertTrue(sm.contains("ROS"))
        Assert.assertTrue(sm.contains("ROSNANO"))
        Assert.assertTrue(sm.contains("GAZ"))
        Assert.assertTrue(sm.contains("GAZGOLDER"))

        with(sm) {
            addWord("SBERCASSA")
            addWord("SBEREGAT")
            addWord("ROSTEL")
            addWord("ROSCOSMOS")
            addWord("GAZ")
        }

        assertThat(sm.size(), Is(equalTo(32)))

        Assert.assertTrue(sm.contains("SBERCASSA"))
        Assert.assertTrue(sm.contains("SBEREGAT"))
        Assert.assertTrue(sm.contains("ROSCOSMOS"))
    }

    @Test
    fun `test addWord() and check contains() returns TRUE Ignore case`() {
        val sm = SearchByPrefixManager(
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

        assertThat(sm.size(), Is(equalTo(34)))

        with(sm) {
            addWord("gAzGOLDer")
            addWord("Bank")
            addWord("bank")
            addWord("Bank of Amer")
            addWord("Bank of Russia")
            addWord("Mailhot")
            addWord("mail")
            addWord("aPPle")
            addWord("aPple Inc.")
            addWord("sberBank")
            addWord("ros")
            addWord("ROS")
            addWord("rosNEFT")
            addWord("rosNEVOd")
            addWord("rosNe")
        }

        assertThat(sm.size(), Is(equalTo(49)))

        Assert.assertTrue(sm.contains("YNDX"))
        Assert.assertTrue(sm.contains("AAPL"))
        Assert.assertTrue(sm.contains("GOOGL"))
        Assert.assertTrue(sm.contains("AMZN"))
        Assert.assertTrue(sm.contains("BAC"))
        Assert.assertTrue(sm.contains("MSFT"))
        Assert.assertTrue(sm.contains("TSLA"))
        Assert.assertTrue(sm.contains("MA"))
        Assert.assertTrue(sm.contains("FB"))
        Assert.assertTrue(sm.contains("GAZP"))
        Assert.assertTrue(sm.contains("ROSN"))
        Assert.assertTrue(sm.contains("GMKN"))
        Assert.assertTrue(sm.contains("SBER"))
        Assert.assertTrue(sm.contains("MAIL"))
        Assert.assertTrue(sm.contains("APPN"))
        Assert.assertTrue(sm.contains("APPF"))
        Assert.assertTrue(sm.contains("APPI"))

        Assert.assertTrue(sm.contains("yndx"))
        Assert.assertTrue(sm.contains("aapl"))
        Assert.assertTrue(sm.contains("googl"))
        Assert.assertTrue(sm.contains("amzn"))
        Assert.assertTrue(sm.contains("bac"))
        Assert.assertTrue(sm.contains("msft"))
        Assert.assertTrue(sm.contains("tsla"))
        Assert.assertTrue(sm.contains("ma"))
        Assert.assertTrue(sm.contains("fb"))
        Assert.assertTrue(sm.contains("gazp"))
        Assert.assertTrue(sm.contains("rosn"))
        Assert.assertTrue(sm.contains("gmkn"))
        Assert.assertTrue(sm.contains("sber"))
        Assert.assertTrue(sm.contains("mail"))
        Assert.assertTrue(sm.contains("appn"))
        Assert.assertTrue(sm.contains("appf"))
        Assert.assertTrue(sm.contains("appi"))

        Assert.assertTrue(sm.contains("YNDX"))
        Assert.assertTrue(sm.contains("AAPL"))
        Assert.assertTrue(sm.contains("GOOGL"))
        Assert.assertTrue(sm.contains("AMZN"))
        Assert.assertTrue(sm.contains("BAC"))
        Assert.assertTrue(sm.contains("MSFT"))
        Assert.assertTrue(sm.contains("TSLA"))
        Assert.assertTrue(sm.contains("MA"))
        Assert.assertTrue(sm.contains("FB"))
        Assert.assertTrue(sm.contains("GAZP"))
        Assert.assertTrue(sm.contains("ROSN"))
        Assert.assertTrue(sm.contains("GMKN"))
        Assert.assertTrue(sm.contains("SBER"))
        Assert.assertTrue(sm.contains("MAIL"))
        Assert.assertTrue(sm.contains("APPN"))
        Assert.assertTrue(sm.contains("APPF"))
        Assert.assertTrue(sm.contains("APPI"))

        Assert.assertTrue(sm.contains("Yandex, LLC"))
        Assert.assertTrue(sm.contains("Apple Inc."))
        Assert.assertTrue(sm.contains("Alphabet Class A"))
        Assert.assertTrue(sm.contains("Amazon.com"))
        Assert.assertTrue(sm.contains("Bank of America Corp"))
        Assert.assertTrue(sm.contains("Microsoft Corporation"))
        Assert.assertTrue(sm.contains("Tesla Motors"))
        Assert.assertTrue(sm.contains("Mastercard"))
        Assert.assertTrue(sm.contains("Facebook"))
        Assert.assertTrue(sm.contains("Gazprom"))
        Assert.assertTrue(sm.contains("Rosneft"))
        Assert.assertTrue(sm.contains("GMK Nor Nickel"))
        Assert.assertTrue(sm.contains("Sberbank"))
        Assert.assertTrue(sm.contains("Mail.ru Group"))
        Assert.assertTrue(sm.contains("Appian Corp."))
        Assert.assertTrue(sm.contains("Appfolio Inc."))
        Assert.assertTrue(sm.contains("Appi Inc."))

        Assert.assertTrue(sm.contains("YANDEX, LLC"))
        Assert.assertTrue(sm.contains("apple inc."))
        Assert.assertTrue(sm.contains("ALPHABET CLASS A"))
        Assert.assertTrue(sm.contains("amazon.com"))
        Assert.assertTrue(sm.contains("BANK OF AMERICA CORP"))
        Assert.assertTrue(sm.contains("microsoft corporation"))
        Assert.assertTrue(sm.contains("TESLA MOTORS"))
        Assert.assertTrue(sm.contains("mastercard"))
        Assert.assertTrue(sm.contains("FACEBOOK"))
        Assert.assertTrue(sm.contains("gazprom"))
        Assert.assertTrue(sm.contains("ROSNEFT"))
        Assert.assertTrue(sm.contains("gmk nor nickel"))
        Assert.assertTrue(sm.contains("SBERBANK"))
        Assert.assertTrue(sm.contains("mail.ru group"))
        Assert.assertTrue(sm.contains("APPIAN CORP."))
        Assert.assertTrue(sm.contains("appfolio inc."))
        Assert.assertTrue(sm.contains("APPI INC."))

        Assert.assertTrue(sm.contains("gAzGOLDer"))
        Assert.assertTrue(sm.contains("Bank"))
        Assert.assertTrue(sm.contains("bank"))
        Assert.assertTrue(sm.contains("Bank of Amer"))
        Assert.assertTrue(sm.contains("Bank of Russia"))
        Assert.assertTrue(sm.contains("Mailhot"))
        Assert.assertTrue(sm.contains("mail"))
        Assert.assertTrue(sm.contains("aPPle"))
        Assert.assertTrue(sm.contains("aPple Inc."))
        Assert.assertTrue(sm.contains("sberBank"))
        Assert.assertTrue(sm.contains("ros"))
        Assert.assertTrue(sm.contains("ROS"))
        Assert.assertTrue(sm.contains("rosNEFT"))
        Assert.assertTrue(sm.contains("rosNEVOd"))
        Assert.assertTrue(sm.contains("rosNe"))

        Assert.assertTrue(sm.contains("GAZgolder"))
        Assert.assertTrue(sm.contains("BANK"))
        Assert.assertTrue(sm.contains("GAZGOLDER"))
        Assert.assertTrue(sm.contains("Bank of AMER"))
        Assert.assertTrue(sm.contains("bank of RUSSIA"))
        Assert.assertTrue(sm.contains("mailhot"))
        Assert.assertTrue(sm.contains("MAIL"))
        Assert.assertTrue(sm.contains("APPLE"))
        Assert.assertTrue(sm.contains("apple Inc."))
        Assert.assertTrue(sm.contains("sberbank"))
        Assert.assertTrue(sm.contains("rOs"))
        Assert.assertTrue(sm.contains("RoS"))
        Assert.assertTrue(sm.contains("rosneft"))
        Assert.assertTrue(sm.contains("rosnevod"))
        Assert.assertTrue(sm.contains("ROSNE"))

        Assert.assertFalse(sm.contains("GAZ"))
        Assert.assertFalse(sm.contains("ban"))
        Assert.assertFalse(sm.contains("gaz"))
        Assert.assertFalse(sm.contains("Bank of Ameri"))
        Assert.assertFalse(sm.contains("bank of Rusia"))
        Assert.assertFalse(sm.contains("mail.hot"))
        Assert.assertFalse(sm.contains("MAIL."))
        Assert.assertFalse(sm.contains("APPLE Inc"))
        Assert.assertFalse(sm.contains("apple Inco"))
        Assert.assertFalse(sm.contains("sber bank"))
        Assert.assertFalse(sm.contains("rOs neft"))
        Assert.assertFalse(sm.contains("RoSNefft"))
        Assert.assertFalse(sm.contains("rosneft."))
        Assert.assertFalse(sm.contains("ros nevod"))
        Assert.assertFalse(sm.contains("ROS.NE"))

        Assert.assertFalse(sm.contains("America"))
        Assert.assertFalse(sm.contains("neft"))
        Assert.assertFalse(sm.contains("PROM"))
        Assert.assertFalse(sm.contains("PPLe"))
        Assert.assertFalse(sm.contains("mAil.ru roup"))
        Assert.assertFalse(sm.contains("Ail.ru group"))
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
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI", "VTB24", "3M",
                    "Yandex, LLC", "Apple Inc.", "Alphabet Class A", "Amazon.com",
                    "Bank of America Corp", "Microsoft Corporation", "Tesla Motors",
                    "Mastercard", "Facebook", "Gazprom", "Rosneft", "GMK Nor Nickel",
                    "Sberbank", "Mail.ru Group", "Appian Corp.", "Appfolio Inc.",
                    "Appi Inc.", "3M&ms", "VneshTorgBank 24"
                ),
                ignoreCase = false
            )
            smallSearchManager = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI", "VTB24", "3M"
                ),
                ignoreCase = false
            )
            searchManagerIgnoreCase = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI", "VTB24", "3M",
                    "Yandex, LLC", "Apple Inc.", "Alphabet Class A", "Amazon.com",
                    "Bank of America Corp", "Microsoft Corporation", "Tesla Motors",
                    "Mastercard", "Facebook", "Gazprom", "Rosneft", "GMK Nor Nickel",
                    "Sberbank", "Mail.ru Group", "Appian Corp.", "Appfolio Inc.",
                    "Appi Inc.", "3m&Ms", "vneshtorgbank 24"
                ),
                ignoreCase = true
            )
            smallSearchManagerIgnoreCase = SearchByPrefixManager(
                dictionary = listOf(
                    "YNDX", "AAPL", "GOOGL", "AMZN", "BAC", "MSFT", "TSLA", "MA", "FB",
                    "GAZP", "ROSN", "GMKN", "SBER", "MAIL", "APPN", "APPF", "APPI", "VTB24", "3M"
                ),
                ignoreCase = true
            )
        }
    }
}
