package com.orcchg.yandexcontest.quotes.domain

import com.orcchg.yandexcontest.coremodel.Quote
import io.reactivex.Completable
import io.reactivex.Flowable

interface RealTimeStocksRepository {

    fun realTimeQuotes(): Flowable<List<Quote>>

    fun invalidate(): Completable
}
