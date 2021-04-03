package com.orcchg.yandexcontest.stocklist.data.finnhub.local.convert

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.finnhub.local.model.IssuerDbo
import com.orcchg.yandexcontest.util.Converter
import java.util.Currency
import javax.inject.Inject

class IssuerDboConverter @Inject constructor() : Converter<IssuerDbo, Issuer> {

    override fun convert(from: IssuerDbo): Issuer =
        Issuer(
            name = from.name,
            country = from.country,
            currency = Currency.getInstance(from.currency),
            ticker = from.ticker,
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )

    override fun revert(from: Issuer): IssuerDbo =
        IssuerDbo(
            name = from.name,
            country = from.country,
            currency = from.currency.currencyCode,
            ticker = from.ticker,
            logoUrl = from.logoUrl,
            isFavourite = from.isFavourite
        )
}
