package com.orcchg.yandexcontest.stocklist.data.remote

import io.reactivex.Maybe

interface IStockListRestForQuote<QuoteEntity> {

    fun quote(ticker: String): Maybe<QuoteEntity>
}
