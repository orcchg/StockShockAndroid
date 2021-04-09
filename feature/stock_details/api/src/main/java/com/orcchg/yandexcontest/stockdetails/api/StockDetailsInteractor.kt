package com.orcchg.yandexcontest.stockdetails.api

import com.orcchg.yandexcontest.stockdetails.api.model.Candle
import io.reactivex.Single

interface StockDetailsInteractor {

    fun candles(
        ticker: String,
        resolution: Candle.Resolution,
        fromTs: Long,
        toTs: Long
    ): Single<List<Candle>>
}
