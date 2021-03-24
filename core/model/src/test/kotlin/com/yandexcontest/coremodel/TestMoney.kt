package com.yandexcontest.coremodel

import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.MoneySign
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.core.Is
import org.junit.Test
import java.util.*

class TestMoney {

    @Test
    fun `test parse money from string`() {
        assertThat(Money.parse("$15"), Is(equalTo(Money.by(15.0, USD))))
        assertThat(Money.parse("15₽"), Is(equalTo(Money.by(15.0, RUB))))
        assertThat(Money.parse("15 ₽"), Is(equalTo(Money.by(15.0, RUB))))
        assertThat(Money.parse("$662.16"), Is(equalTo(Money.by(662.16, USD))))
        assertThat(Money.parse("$3,137.50"), Is(equalTo(Money.by(3137.5, USD))))
        assertThat(Money.parse("2 094.21 ₽"), Is(equalTo(Money.by(2094.21, RUB))))
        assertThat(Money.parse("1 177,80 ₽"), Is(equalTo(Money.by(1177.8, RUB))))
        assertThat(Money.parse("4,168.21 ₽"), Is(equalTo(Money.by(4168.21, RUB))))
        assertThat(Money.parse("500₽"), Is(equalTo(Money.by(500.0, RUB))))
        assertThat(Money.parse("500.50₽"), Is(equalTo(Money.by(500.5, RUB))))
        assertThat(Money.parse("500.50 ₽"), Is(equalTo(Money.by(500.5, RUB))))

        assertThat(Money.parse("$15"), Is(equalTo(Money.by(15.0, USD, MoneySign.PLUS))))
        assertThat(Money.parse("15₽"), Is(equalTo(Money.by(15.0, RUB, MoneySign.PLUS))))
        assertThat(Money.parse("15 ₽"), Is(equalTo(Money.by(15.0, RUB, MoneySign.PLUS))))
        assertThat(Money.parse("$662.16"), Is(equalTo(Money.by(662.16, USD, MoneySign.PLUS))))
        assertThat(Money.parse("$3,137.50"), Is(equalTo(Money.by(3137.5, USD, MoneySign.PLUS))))
        assertThat(Money.parse("2 094.21 ₽"), Is(equalTo(Money.by(2094.21, RUB, MoneySign.PLUS))))
        assertThat(Money.parse("1 177,80 ₽"), Is(equalTo(Money.by(1177.8, RUB, MoneySign.PLUS))))
        assertThat(Money.parse("4,168.21 ₽"), Is(equalTo(Money.by(4168.21, RUB, MoneySign.PLUS))))
        assertThat(Money.parse("500₽"), Is(equalTo(Money.by(500.0, RUB, MoneySign.PLUS))))
        assertThat(Money.parse("500.50₽"), Is(equalTo(Money.by(500.5, RUB, MoneySign.PLUS))))
        assertThat(Money.parse("500.50 ₽"), Is(equalTo(Money.by(500.5, RUB, MoneySign.PLUS))))
    }

    @Test
    fun `test parse money from string negative amount`() {
        assertThat(Money.parse("-$15"), Is(equalTo(Money.by(-15.0, USD))))
        assertThat(Money.parse("-15₽"), Is(equalTo(Money.by(-15.0, RUB))))
        assertThat(Money.parse("-15 ₽"), Is(equalTo(Money.by(-15.0, RUB))))
        assertThat(Money.parse("-$662.16"), Is(equalTo(Money.by(-662.16, USD))))
        assertThat(Money.parse("-$3,137.50"), Is(equalTo(Money.by(-3137.5, USD))))
        assertThat(Money.parse("-2 094.21 ₽"), Is(equalTo(Money.by(-2094.21, RUB))))
        assertThat(Money.parse("-1 177,80 ₽"), Is(equalTo(Money.by(-1177.8, RUB))))
        assertThat(Money.parse("-4,168.21 ₽"), Is(equalTo(Money.by(-4168.21, RUB))))
        assertThat(Money.parse("-500₽"), Is(equalTo(Money.by(-500.0, RUB))))
        assertThat(Money.parse("-500.50₽"), Is(equalTo(Money.by(-500.5, RUB))))
        assertThat(Money.parse("-500.50 ₽"), Is(equalTo(Money.by(-500.5, RUB))))

        assertThat(Money.parse("-$15"), Is(equalTo(Money.by(15.0, USD, MoneySign.MINUS))))
        assertThat(Money.parse("-15₽"), Is(equalTo(Money.by(15.0, RUB, MoneySign.MINUS))))
        assertThat(Money.parse("-15 ₽"), Is(equalTo(Money.by(15.0, RUB, MoneySign.MINUS))))
        assertThat(Money.parse("-$662.16"), Is(equalTo(Money.by(662.16, USD, MoneySign.MINUS))))
        assertThat(Money.parse("-$3,137.50"), Is(equalTo(Money.by(3137.5, USD, MoneySign.MINUS))))
        assertThat(Money.parse("-2 094.21 ₽"), Is(equalTo(Money.by(2094.21, RUB, MoneySign.MINUS))))
        assertThat(Money.parse("-1 177,80 ₽"), Is(equalTo(Money.by(1177.8, RUB, MoneySign.MINUS))))
        assertThat(Money.parse("-4,168.21 ₽"), Is(equalTo(Money.by(4168.21, RUB, MoneySign.MINUS))))
        assertThat(Money.parse("-500₽"), Is(equalTo(Money.by(500.0, RUB, MoneySign.MINUS))))
        assertThat(Money.parse("-500.50₽"), Is(equalTo(Money.by(500.5, RUB, MoneySign.MINUS))))
        assertThat(Money.parse("-500.50 ₽"), Is(equalTo(Money.by(500.5, RUB, MoneySign.MINUS))))
    }

    companion object {
        val RUB: Currency = Currency.getInstance(Locale("ru", "RU"))
        val USD: Currency = Currency.getInstance(Locale.US)
    }
}
