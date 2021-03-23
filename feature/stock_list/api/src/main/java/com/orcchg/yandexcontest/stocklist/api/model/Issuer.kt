package com.orcchg.yandexcontest.stocklist.api.model

import java.util.*

data class Issuer(
    val name: String,
    val country: String,
    val currency: Currency,
    val ticker: String,
    val logoUrl: String? = null,
    val isFavourite: Boolean
)
