package com.orcchg.yandexcontest.stocklist.api

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import io.reactivex.Single

interface StockListInteractor {

    fun issuers(): Single<List<Issuer>>
}
