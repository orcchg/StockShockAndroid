package com.orcchg.yandexcontest.stocklist.data.wiring

import com.orcchg.yandexcontest.coredi.PublishedNoReasonableAlternatives
import com.orcchg.yandexcontest.stocklist.data.api.RealTimeStocksRepository
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import com.orcchg.yandexcontest.stocklist.data.RealTimeStocksRepositoryImpl
import com.orcchg.yandexcontest.stocklist.data.StockListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(
    includes = [
        StockListDatabaseModule::class,
        StockListNetworkModule::class
    ]
)
@PublishedNoReasonableAlternatives
interface StockListDataModule {

    @Binds
    @Reusable
    fun repository(impl: StockListRepositoryImpl): StockListRepository

    @Binds
    @Reusable
    fun realTimeRepository(impl: RealTimeStocksRepositoryImpl): RealTimeStocksRepository
}
