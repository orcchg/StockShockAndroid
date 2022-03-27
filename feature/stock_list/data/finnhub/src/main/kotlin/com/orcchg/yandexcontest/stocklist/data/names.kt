package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.coremodel.Locale

fun refineCompanyName(raw: String): String =
    raw.trim()
        .lowercase(Locale.DEFAULT)
        .removeSuffix(" ord")
        .split(' ')
        .joinToString(" ") {
            it.replaceFirstChar { c ->
                if (c.isLowerCase()) c.titlecase(Locale.DEFAULT)
                else c.toString()
            }
        }
