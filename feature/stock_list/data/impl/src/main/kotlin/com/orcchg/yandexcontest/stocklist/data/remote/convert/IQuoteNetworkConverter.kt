package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.stocklist.api.model.Quote

interface IQuoteNetworkConverter<QuoteEntity> {

    fun convert(ticker: String, from: QuoteEntity): Quote
}
