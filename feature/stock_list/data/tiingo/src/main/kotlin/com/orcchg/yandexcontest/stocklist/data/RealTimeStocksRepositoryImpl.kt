package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.api.RealTimeStocksRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class RealTimeStocksRepositoryImpl @Inject constructor(
) : RealTimeStocksRepository {

    // TODO: implement
    override fun realTimeQuotes(): Flowable<List<Quote>> =
        Flowable.just(emptyList())

    override fun invalidate(): Completable = Completable.complete()
}
