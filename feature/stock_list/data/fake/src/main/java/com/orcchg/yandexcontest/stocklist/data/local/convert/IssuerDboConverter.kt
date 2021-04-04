package com.orcchg.yandexcontest.stocklist.data.local.convert

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.util.Converter
import java.util.*
import javax.inject.Inject

class IssuerDboConverter @Inject constructor() : Converter<IssuerDbo, Issuer> {

    override fun convert(from: IssuerDbo): Issuer =
        Issuer(
            name = from.ticker,
            country = "US",
            currency = Currency.getInstance(Locale.US),
            ticker = from.ticker,
            isFavourite = from.isFavourite
        )
}
