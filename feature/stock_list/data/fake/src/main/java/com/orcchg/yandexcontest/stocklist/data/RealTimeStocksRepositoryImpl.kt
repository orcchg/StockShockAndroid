package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.coremodel.times
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.api.RealTimeStocksRepository
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.QuoteDao
import com.orcchg.yandexcontest.stocklist.data.local.convert.QuoteDboConverter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RealTimeStocksRepositoryImpl @Inject constructor(
    private val localIssuer: IssuerDao,
    private val localQuotes: QuoteDao,
    private val quoteLocalConverter: QuoteDboConverter
) : RealTimeStocksRepository {

    override fun realTimeQuotes(): Flowable<List<Quote>> =
        localIssuer.issuers()
            .flatMap {
                Observable.fromIterable(it).take(10)
                    .flatMapMaybe { issuer ->
                        localQuotes.quote(issuer.ticker).map(quoteLocalConverter::convert)
                    }
                    .toList()
            }
            .flatMapPublisher { quotes ->
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                    .flatMapSingle { value ->
                        Flowable.fromIterable(quotes)
                            .map {
                                val percent = it.currentPrice * (value * 0.01)
                                it.copy(currentPrice = it.currentPrice + percent)
                            }
                            .toList()
                    }
            }

    override fun invalidate(): Completable = Completable.complete() // operation not supported
}
