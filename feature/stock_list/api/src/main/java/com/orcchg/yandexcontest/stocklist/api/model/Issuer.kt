package com.orcchg.yandexcontest.stocklist.api.model

data class Issuer(
    val name: String,
    val ticker: String,
    val logoUrl: String? = null
)
