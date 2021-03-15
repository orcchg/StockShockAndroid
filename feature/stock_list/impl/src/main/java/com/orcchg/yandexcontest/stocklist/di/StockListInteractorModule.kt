package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.stocklist.StockListInteractorImpl
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        StockListDataModule::class,
        StockListNetworkModule::class,
    ]
)
@PublishedWithReasonableAlternatives(binding = StockListInteractor::class)
interface StockListInteractorModule {

    @Binds
    fun interactor(impl: StockListInteractorImpl): StockListInteractor
}
