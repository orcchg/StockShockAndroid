package com.orcchg.yandexcontest.stocklist.domain

import com.orcchg.yandexcontest.stocklist.api.model.Quote
import io.reactivex.Flowable

interface RealTimeStocksRepository {

    fun realTimeQuotes(): Flowable<List<Quote>>
}
