package com.orcchg.yandexcontest.stocklist.data.finnhub.remote.convert

import com.orcchg.yandexcontest.stocklist.data.finnhub.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.finnhub.refineCompanyName
import com.orcchg.yandexcontest.stocklist.data.finnhub.remote.model.IssuerEntity
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class IssuerNetworkToDboConverter @Inject constructor() : Converter<IssuerEntity, IssuerDbo> {

    override fun convert(from: IssuerEntity): IssuerDbo =
        IssuerDbo(
            ticker = from.ticker,
            country = from.country,
            currency = from.currency,
            name = refineCompanyName(from.name),
            logoUrl = from.logoUrl
        )
}
