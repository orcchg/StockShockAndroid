package com.orcchg.yandexcontest.stocklist

import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import io.reactivex.Single
import javax.inject.Inject

class StockListInteractorImpl @Inject constructor() : StockListInteractor {

    override fun issuers(): Single<List<Issuer>> = Single.just(emptyList())
}
