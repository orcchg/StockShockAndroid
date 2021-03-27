package com.orcchg.yandexcontest.quotes

import com.orcchg.yandexcontest.coremodel.Quote
import com.orcchg.yandexcontest.quotes.api.RealTimeQuotesInteractor
import com.orcchg.yandexcontest.quotes.domain.usecase.GetRealTimeQuotesUseCase
import com.orcchg.yandexcontest.quotes.domain.usecase.ResubscribeRealTimeQuotesUseCase
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("CheckResult")
class RealTimeQuotesInteractorImpl @Inject constructor(
    private val resubscribeRealTimeQuotesUseCase: ResubscribeRealTimeQuotesUseCase,
    getRealTimeQuotesUseCase: GetRealTimeQuotesUseCase,
    schedulersFactory: SchedulersFactory
) : RealTimeQuotesInteractor {

    private val _realTimeQuotes = PublishSubject.create<Collection<Quote>>()
    private val realTimeQuotesStorage = LinkedHashMap<String, Quote>()
    private fun addRealTimeQuote(quote: Quote) {
        realTimeQuotesStorage[quote.ticker] = quote
    }
    @Synchronized
    private fun addRealTimeQuotes(quotes: Collection<Quote>) {
        quotes.forEach(::addRealTimeQuote)
    }
    @Synchronized
    private fun snapshotRealTimeQuotes(): Collection<Quote> =
        mutableListOf<Quote>()
            .apply { addAll(realTimeQuotesStorage.values) }
            .also { realTimeQuotesStorage.clear() }

    init {
        getRealTimeQuotesUseCase.source(schedulersFactory.io())
            .filter { it.isNotEmpty() }
            .doOnNext { Timber.v("RT-quotes: ${it.joinToString { s -> "W-[${s.ticker}:${s.currentPrice}]" }}") }
            .subscribe(::addRealTimeQuotes, Timber::e)
    }

    override val realTimeQuotes: Observable<Collection<Quote>> = _realTimeQuotes.hide()
}
