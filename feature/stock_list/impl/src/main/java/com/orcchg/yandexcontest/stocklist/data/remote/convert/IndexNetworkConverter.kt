package com.orcchg.yandexcontest.stocklist.data.remote.convert

import com.orcchg.yandexcontest.stocklist.api.model.Index
import com.orcchg.yandexcontest.stocklist.data.remote.model.IndexEntity
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class IndexNetworkConverter @Inject constructor() : Converter<IndexEntity, Index> {

    override fun convert(from: IndexEntity): Index =
        Index(tickers = from.tickers.take(30), name = from.name)
}
