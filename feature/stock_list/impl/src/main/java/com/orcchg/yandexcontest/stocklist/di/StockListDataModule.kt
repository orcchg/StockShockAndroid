package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.stocklist.data.RealTimeStocksRepositoryImpl
import com.orcchg.yandexcontest.stocklist.data.StockListRepositoryImpl
import com.orcchg.yandexcontest.stocklist.domain.RealTimeStocksRepository
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(
    includes = [
        StockListDatabaseModule::class,
        StockListNetworkModule::class
    ]
)
@InternalBindings
interface StockListDataModule {

    @Binds
    @Reusable
    fun repository(impl: StockListRepositoryImpl): StockListRepository

    @Binds
    @Reusable
    fun realTimeRepository(impl: RealTimeStocksRepositoryImpl): RealTimeStocksRepository
}
