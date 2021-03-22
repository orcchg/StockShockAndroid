package com.orcchg.yandexcontest.fake.data

import com.orcchg.yandexcontest.stocklist.api.model.Issuer

internal val fakeIssuers by lazy {
    listOf(
        Issuer(
            name = "Yandex, LLC",
            ticker = "YNDX",
            isFavourite = false
        ),
        Issuer(
            name = "Apple Inc.",
            ticker = "AAPL",
            isFavourite = true
        ),
        Issuer(
            name = "Alphabet Class A",
            ticker = "GOOGL",
            isFavourite = false
        ),
        Issuer(
            name = "Amazon.com",
            ticker = "AMZN",
            isFavourite = false
        ),
        Issuer(
            name = "Bank of America Corp",
            ticker = "BAC",
            isFavourite = false
        ),
        Issuer(
            name = "Microsoft Corporation",
            ticker = "MSFT",
            isFavourite = true
        ),
        Issuer(
            name = "Tesla Motors",
            ticker = "TSLA",
            isFavourite = true
        ),
        Issuer(
            name = "Mastercard",
            ticker = "MA",
            isFavourite = false
        ),
        Issuer(
            name = "Facebook",
            ticker = "FB",
            isFavourite = false
        ),
        Issuer(
            name = "Gazprom",
            ticker = "GAZP",
            isFavourite = false
        ),
        Issuer(
            name = "Rosneft",
            ticker = "ROSN",
            isFavourite = false
        ),
        Issuer(
            name = "GMK Nor Nickel",
            ticker = "GMKN",
            isFavourite = false
        ),
        Issuer(
            name = "Sberbank",
            ticker = "SBER",
            isFavourite = false
        ),
        Issuer(
            name = "Mail.ru Group",
            ticker = "MAIL",
            isFavourite = false
        ),
        Issuer(
            name = "Appian Corp.",
            ticker = "APPN",
            isFavourite = false
        ),
        Issuer(
            name = "Appfolio Inc.",
            ticker = "APPF",
            isFavourite = false
        ),
        Issuer(
            name = "Appi Inc.",
            ticker = "APPI",
            isFavourite = false
        )
    )
}

internal val fakeIssuersMap by lazy(LazyThreadSafetyMode.NONE) {
    val map = HashMap<String, Issuer>()
    for (issuer in fakeIssuers) {
        map[issuer.ticker] = issuer
        map[issuer.name] = issuer
    }
    map
}

internal fun getIssuer(nameOrTicker: String): Issuer =
    fakeIssuersMap[nameOrTicker] ?: throw IllegalArgumentException("No issuer found by name or ticker: $nameOrTicker")
