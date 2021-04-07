package com.orcchg.yandexcontest.stockdetails.fake.data

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.stockdetails.api.model.Candle

internal val mapCandles by lazy(LazyThreadSafetyMode.NONE) {
    mutableMapOf<String, List<Candle>>().apply {
        put("YNDX", listOf())
        put("GOOGL", GOOGL.m1)
        put("AAPL", AAPL.m1)
        put("MSFT", listOf())
        put("MRNA", listOf())
        put("TSLA", listOf())
    }
}

internal fun filler(
    openPrices: List<Number>,
    maxPrices: List<Number>,
    minPrices: List<Number>,
    closePrices: List<Number>,
    resolution: Candle.Resolution,
    volumes: List<Long>,
    ts: List<Long>
): List<Candle> =
    mutableListOf<Candle>().apply {
        val size = listOf(openPrices.size, maxPrices.size, minPrices.size, closePrices.size, volumes.size, ts.size).minOrNull()
            ?: return@apply

        for (i in 0 until size) {
            val candle = Candle(
                ticker = "AAPL",
                openPrice = openPrices[i].toDouble().money(),
                maxPrice = maxPrices[i].toDouble().money(),
                minPrice = minPrices[i].toDouble().money(),
                closePrice = closePrices[i].toDouble().money(),
                resolution = resolution,
                volume = volumes[i],
                ts = ts[i]
            )
            add(candle)
        }
    }
