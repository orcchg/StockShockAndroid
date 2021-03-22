package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.StockListRest
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import io.reactivex.Single
import javax.inject.Inject

class StockListRepositoryImpl @Inject constructor(
    private val cloud: StockListRest,
    private val localIssuer: IssuerDao,
    private val issuerDboConverter: IssuerDboConverter
) : StockListRepository {

    override fun defaultIssuers(): Single<List<Issuer>> =
        Single.just(emptyList())

    override fun favouriteIssuers(): Single<List<Issuer>> =
        localIssuer.favouriteIssuers().map(issuerDboConverter::convertList)
}
