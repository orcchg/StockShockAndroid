package com.orcchg.yandexcontest.stocklist.data.remote

import io.reactivex.Single

interface IStockListRestForQuote<QuoteEntity> {

    fun quote(ticker: String): Single<QuoteEntity>
}
