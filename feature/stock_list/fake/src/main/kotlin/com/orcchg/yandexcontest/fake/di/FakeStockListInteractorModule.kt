package com.orcchg.yandexcontest.fake.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.fake.FakeStockListInteractor
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
@PublishedWithReasonableAlternatives(binding = StockListInteractor::class)
interface FakeStockListInteractorModule {

    @Binds
    @Reusable
    fun interactor(impl: FakeStockListInteractor): StockListInteractor
}
