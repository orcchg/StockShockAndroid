package com.orcchg.yandexcontest.stocklist.domain

import com.orcchg.yandexcontest.coremodel.Quote
import io.reactivex.Completable
import io.reactivex.Flowable

@Deprecated("quotes")
interface RealTimeStocksRepository {

    fun realTimeQuotes(): Flowable<List<Quote>>

    fun invalidate(): Completable
}
