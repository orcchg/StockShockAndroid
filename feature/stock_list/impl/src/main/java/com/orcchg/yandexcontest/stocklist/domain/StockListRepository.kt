package com.orcchg.yandexcontest.stocklist.domain

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import io.reactivex.Single

interface StockListRepository {

    fun defaultIssuers(): Single<List<Issuer>>
    fun favouriteIssuers(): Single<List<Issuer>>
}
