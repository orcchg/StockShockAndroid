package com.orcchg.yandexcontest.stocklist.data.local.convert

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class IssuerDboConverter @Inject constructor() : Converter<IssuerDbo, Issuer> {

    override fun convert(from: IssuerDbo): Issuer =
        Issuer(
            name = from.name,
            ticker = from.ticker,
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )

    override fun revert(from: Issuer): IssuerDbo =
        IssuerDbo(
            name = from.name,
            ticker = from.ticker,
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )
}
