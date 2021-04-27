package com.orcchg.yandexcontest.stockdetails.fake

import com.orcchg.yandexcontest.stockdetails.api.StockDetailsInteractor
import com.orcchg.yandexcontest.stockdetails.api.model.Candle
import com.orcchg.yandexcontest.stockdetails.fake.data.mapCandles
import io.reactivex.Single
import javax.inject.Inject

class FakeStockDetailsInteractor @Inject constructor() : StockDetailsInteractor {

    override fun candles(
        ticker: String,
        resolution: Candle.Resolution,
        fromTs: Long,
        toTs: Long
    ): Single<List<Candle>> =
        Single.just(mapCandles[ticker])
}
