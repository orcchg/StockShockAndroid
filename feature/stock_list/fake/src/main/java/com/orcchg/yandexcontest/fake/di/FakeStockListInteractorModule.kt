package com.orcchg.yandexcontest.fake.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.fake.FakeStockListInteractor
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import dagger.Binds
import dagger.Module

@Module
@PublishedWithReasonableAlternatives(binding = StockListInteractor::class)
interface FakeStockListInteractorModule {

    @Binds
    fun interactor(impl: FakeStockListInteractor): StockListInteractor
}
