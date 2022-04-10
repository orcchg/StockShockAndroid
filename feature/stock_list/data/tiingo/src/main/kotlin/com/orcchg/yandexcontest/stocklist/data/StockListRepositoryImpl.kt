package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagManager
import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Index
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.QuoteDao
import com.orcchg.yandexcontest.stocklist.data.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.local.convert.QuoteDboConverter
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.remote.IStockListRestForIndex
import com.orcchg.yandexcontest.stocklist.data.remote.IStockListRestForIssuer
import com.orcchg.yandexcontest.stocklist.data.remote.IStockListRestForQuote
import com.orcchg.yandexcontest.stocklist.data.remote.StockListRest
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IQuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IndexNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IssuerNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IssuerNetworkToDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.QuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.model.IndexEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.IssuerEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import com.orcchg.yandexcontest.util.Converter
import javax.inject.Inject

class StockListRepositoryImpl @Inject constructor(
    private val restCloud: StockListRest,
    private val indexNetworkConverter: IndexNetworkConverter,
    private val issuerNetworkConverter: IssuerNetworkConverter,
    private val issuerNetworkToLocalConverter: IssuerNetworkToDboConverter,
    private val quoteNetworkConverter: QuoteNetworkConverter,
    issuerLocal: IssuerDao,
    quoteLocal: QuoteDao,
    issuerLocalConverter: IssuerDboConverter,
    quoteLocalConverter: QuoteDboConverter,
    featureFlagManager: FeatureFlagManager,
    schedulersFactory: SchedulersFactory
) : BaseStockListRepositoryImpl<IndexEntity, IssuerEntity, QuoteEntity>(
    issuerLocal,
    quoteLocal,
    issuerLocalConverter,
    quoteLocalConverter,
    featureFlagManager,
    schedulersFactory
), StockListRepository {
    override fun indexRest(): IStockListRestForIndex<IndexEntity> = restCloud
    override fun issuerRest(): IStockListRestForIssuer<IssuerEntity> = restCloud
    override fun quoteRest(): IStockListRestForQuote<QuoteEntity> = restCloud

    override fun indexNetworkConverter(): Converter<IndexEntity, Index> = indexNetworkConverter
    override fun issuerNetworkConverter(): Converter<IssuerEntity, Issuer> = issuerNetworkConverter
    override fun issuerNetworkToLocalConverter(): Converter<IssuerEntity, IssuerDbo> = issuerNetworkToLocalConverter
    override fun quoteNetworkConverter(): IQuoteNetworkConverter<QuoteEntity> = quoteNetworkConverter

    override fun defaultQuoteEntity(): QuoteEntity = QuoteEntity()
}
