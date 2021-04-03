package com.orcchg.yandexcontest.stocklist.data.finnhub

import com.orcchg.yandexcontest.coremodel.Locale

fun refineCompanyName(raw: String): String =
    raw.trim()
        .toLowerCase(Locale.DEFAULT)
        .removeSuffix(" ord")
        .split(' ')
        .map { it.capitalize(Locale.DEFAULT) }
        .joinToString(" ")
