package com.orcchg.yandexcontest.stocklist.data.remote

import io.reactivex.Single

interface IStockListRestForIssuer<IssuerEntity> {

    fun issuer(ticker: String): Single<IssuerEntity>
}
