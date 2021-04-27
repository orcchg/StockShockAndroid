package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.refineCompanyName
import com.orcchg.yandexcontest.stocklist.data.remote.model.IssuerEntity
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
