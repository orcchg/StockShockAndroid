package com.orcchg.yandexcontest.stockdetails.fake.data

import com.orcchg.yandexcontest.stockdetails.api.model.Candle

// TODO: map by ticket
internal val fakeCandles by lazy {
    listOf(
        Candle(
            ticker = "YNDX",
            resolution = Candle.Resolution.Day
        ),
        Candle(
            ticker = "GOOGL",
            resolution = Candle.Resolution.Day
        ),
        Candle(
            ticker = "AAPL",
            resolution = Candle.Resolution.Day
        ),
        Candle(
            ticker = "MSFT",
            resolution = Candle.Resolution.Day
        ),
        Candle(
            ticker = "MRNA",
            resolution = Candle.Resolution.Day
        ),
        Candle(
            ticker = "TSLA",
            resolution = Candle.Resolution.Day
        )
    )
}
