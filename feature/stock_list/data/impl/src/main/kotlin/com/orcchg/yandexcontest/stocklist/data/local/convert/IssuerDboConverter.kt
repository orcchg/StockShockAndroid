package com.orcchg.yandexcontest.stocklist.data.local.convert

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.util.Converter
import dagger.Reusable
import java.util.*
import javax.inject.Inject

@Reusable
class IssuerDboConverter @Inject constructor() : Converter<IssuerDbo, Issuer> {

    override fun convert(from: IssuerDbo): Issuer =
        Issuer(
            name = from.name,
            country = from.country,
            currency = from.currency?.let(Currency::getInstance),
            ticker = from.ticker,
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )

    override fun revert(from: Issuer): IssuerDbo =
        IssuerDbo(
            name = from.name,
            country = from.country,
            currency = from.currency?.currencyCode,
            ticker = from.ticker,
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )
}
