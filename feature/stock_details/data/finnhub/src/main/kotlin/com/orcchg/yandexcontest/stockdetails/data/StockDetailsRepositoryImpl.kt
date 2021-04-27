package com.orcchg.yandexcontest.stockdetails.data

import com.orcchg.yandexcontest.stockdetails.api.model.Candle
import com.orcchg.yandexcontest.stockdetails.data.api.StockDetailsRepository
import com.orcchg.yandexcontest.stockdetails.data.remote.StockDetailsRest
import com.orcchg.yandexcontest.stockdetails.data.remote.convert.CandleNetworkConverter
import io.reactivex.Single
import javax.inject.Inject

class StockDetailsRepositoryImpl @Inject constructor(
    private val restCloud: StockDetailsRest,
    private val candleNetworkConverter: CandleNetworkConverter
) : StockDetailsRepository {

    override fun candles(
        ticker: String,
        resolution: Candle.Resolution,
        fromTs: Long,
        toTs: Long
    ): Single<List<Candle>> =
        restCloud.candles(
            ticker = ticker,
            resolution = resolution.v,
            fromTs = fromTs,
            toTs = toTs
        )
            .map {
                candleNetworkConverter.convert(
                    ticker = ticker,
                    resolution = resolution,
                    from = it
                )
            }
}
