package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.stocklist.StockListInteractorImpl
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(
    includes = [
        StockListDataModule::class
    ]
)
@PublishedWithReasonableAlternatives(binding = StockListInteractor::class)
interface StockListInteractorModule {

    @Binds
    @Reusable
    fun interactor(impl: StockListInteractorImpl): StockListInteractor
}
