package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.remote.model.IssuerEntity
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class IssuerNetworkConverter @Inject constructor() : Converter<IssuerEntity, Issuer> {

    override fun convert(from: IssuerEntity): Issuer =
        Issuer(
            name = from.name,
            ticker = from.ticker,
            isFavourite = false
        )
}
