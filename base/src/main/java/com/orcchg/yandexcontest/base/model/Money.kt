package com.orcchg.yandexcontest.base.model

import java.math.BigDecimal

data class Money(
    val amount: BigDecimal
) {

    override fun toString(): String = "$amount" // TODO: format money
}
