package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.stocklist.data.StockListRepositoryImpl
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import dagger.Module

@Module
@InternalBindings
interface StockListDataModule {

    fun repository(impl: StockListRepositoryImpl): StockListRepository
}
