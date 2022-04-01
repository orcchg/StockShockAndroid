package com.orcchg.yandexcontest.stocklist.data.remote

import io.reactivex.Single

interface IStockListRestForIndex<IndexEntity> {

    fun index(symbol: String): Single<IndexEntity>
}
