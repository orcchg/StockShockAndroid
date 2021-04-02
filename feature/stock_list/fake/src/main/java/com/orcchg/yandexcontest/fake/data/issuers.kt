package com.orcchg.yandexcontest.fake.data

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import java.util.Currency
import java.util.Locale
import kotlin.collections.HashMap
import com.orcchg.yandexcontest.coremodel.Locale as locale

internal val fakeIssuers by lazy {
    listOf(
        Issuer(
            name = "Yandex, LLC",
            ticker = "YNDX",
            isFavourite = false,
            country = "Russia",
            currency = Currency.getInstance(locale.RUSSIA)
        ),
        Issuer(
            name = "Apple Inc.",
            ticker = "AAPL",
            isFavourite = true,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Alphabet Class A",
            ticker = "GOOGL",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Amazon.com",
            ticker = "AMZN",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Bank of America Corp",
            ticker = "BAC",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Microsoft Corporation",
            ticker = "MSFT",
            isFavourite = true,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Tesla Motors",
            ticker = "TSLA",
            isFavourite = true,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Mastercard",
            ticker = "MA",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Facebook",
            ticker = "FB",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Gazprom",
            ticker = "GAZP",
            isFavourite = false,
            country = "Russia",
            currency = Currency.getInstance(locale.RUSSIA)
        ),
        Issuer(
            name = "Rosneft",
            ticker = "ROSN",
            isFavourite = false,
            country = "Russia",
            currency = Currency.getInstance(locale.RUSSIA)
        ),
        Issuer(
            name = "GMK Nor Nickel",
            ticker = "GMKN",
            isFavourite = false,
            country = "Russia",
            currency = Currency.getInstance(locale.RUSSIA)
        ),
        Issuer(
            name = "Sberbank",
            ticker = "SBER",
            isFavourite = false,
            country = "Russia",
            currency = Currency.getInstance(locale.RUSSIA)
        ),
        Issuer(
            name = "Mail.ru Group",
            ticker = "MAIL",
            isFavourite = false,
            country = "Russia",
            currency = Currency.getInstance(locale.RUSSIA)
        ),
        Issuer(
            name = "Appian Corp.",
            ticker = "APPN",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Appfolio Inc.",
            ticker = "APPF",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
        ),
        Issuer(
            name = "Appi Inc.",
            ticker = "APPI",
            isFavourite = false,
            country = "United States",
            currency = Currency.getInstance(Locale.US)
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
