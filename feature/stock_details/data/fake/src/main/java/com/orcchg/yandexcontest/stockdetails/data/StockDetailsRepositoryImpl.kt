package com.orcchg.yandexcontest.stockdetails.data

import com.orcchg.yandexcontest.stockdetails.api.model.Candle
import com.orcchg.yandexcontest.stockdetails.data.api.StockDetailsRepository
import io.reactivex.Single
import javax.inject.Inject

class StockDetailsRepositoryImpl @Inject constructor() : StockDetailsRepository {

    override fun candles(
        ticker: String,
        resolution: Candle.Resolution,
        fromTs: Long,
        toTs: Long
    ): Single<List<Candle>> =
        Single.just(emptyList()) // TODO: fake data
}
