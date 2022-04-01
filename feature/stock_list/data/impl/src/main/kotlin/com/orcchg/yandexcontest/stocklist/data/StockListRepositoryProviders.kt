package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.stocklist.api.model.Index
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.local.model.QuoteDbo
import com.orcchg.yandexcontest.stocklist.data.remote.IStockListRestForIndex
import com.orcchg.yandexcontest.stocklist.data.remote.IStockListRestForIssuer
import com.orcchg.yandexcontest.stocklist.data.remote.IStockListRestForQuote
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IQuoteNetworkConverter
import com.orcchg.yandexcontest.util.Converter

interface StockListRepositoryProviders<IndexEntity, IssuerEntity, QuoteEntity> {

    fun indexRest(): IStockListRestForIndex<IndexEntity>
    fun issuerRest(): IStockListRestForIssuer<IssuerEntity>
    fun quoteRest(): IStockListRestForQuote<QuoteEntity>

    fun indexNetworkConverter(): Converter<IndexEntity, Index>
    fun issuerNetworkConverter(): Converter<IssuerEntity, Issuer>
    fun issuerNetworkToLocalConverter(): Converter<IssuerEntity, IssuerDbo>
    fun quoteNetworkConverter(): IQuoteNetworkConverter<QuoteEntity>

    fun defaultQuoteEntity(): QuoteEntity

    fun httpErrorCodeTooManyRequests(): Int = 429
}
