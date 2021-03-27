package com.orcchg.yandexcontest.quotes.api

import com.orcchg.yandexcontest.coremodel.Quote
import io.reactivex.Observable

interface RealTimeQuotesInteractor {

    val realTimeQuotes: Observable<Collection<Quote>>
}
