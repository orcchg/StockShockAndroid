package com.orcchg.yandexcontest.stockdetails.data.api

import com.orcchg.yandexcontest.stockdetails.api.model.Candle
import io.reactivex.Single

interface StockDetailsRepository {

    fun candles(
        ticker: String,
        resolution: Candle.Resolution,
        fromTs: Long,
        toTs: Long
    ): Single<List<Candle>>
}
