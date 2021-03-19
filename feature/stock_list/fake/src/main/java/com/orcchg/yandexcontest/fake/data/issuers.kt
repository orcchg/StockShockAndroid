package com.orcchg.yandexcontest.fake.data

import com.orcchg.yandexcontest.stocklist.api.model.Issuer

internal val fakeIssuers by lazy {
    listOf(
        Issuer(
            name = "Yandex, LLC",
            ticker = "YNDX"
        ),
        Issuer(
            name = "Apple Inc.",
            ticker = "AAPL"
        ),
        Issuer(
            name = "Alphabet Class A",
            ticker = "GOOGL"
        ),
        Issuer(
            name = "Amazon.com",
            ticker = "AMZN"
        ),
        Issuer(
            name = "Bank of America Corp",
            ticker = "BAC"
        ),
        Issuer(
            name = "Microsoft Corporation",
            ticker = "MSFT"
        ),
        Issuer(
            name = "Tesla Motors",
            ticker = "TSLA"
        ),
        Issuer(
            name = "Mastercard",
            ticker = "MA"
        ),
        Issuer(
            name = "Facebook",
            ticker = "FB"
        ),
        Issuer(
            name = "Gazprom",
            ticker = "GAZP"
        ),
        Issuer(
            name = "Rosneft",
            ticker = "ROSN"
        ),
        Issuer(
            name = "GMK Nor Nickel",
            ticker = "GMKN"
        ),
        Issuer(
            name = "Sberbank",
            ticker = "SBER"
        ),
        Issuer(
            name = "Mail.ru Group",
            ticker = "MAIL"
        ),
        Issuer(
            name = "Appian Corp.",
            ticker = "APPN"
        ),
        Issuer(
            name = "Appfolio Inc.",
            ticker = "APPF"
        ),
        Issuer(
            name = "Appi Inc.",
            ticker = "APPI",
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
