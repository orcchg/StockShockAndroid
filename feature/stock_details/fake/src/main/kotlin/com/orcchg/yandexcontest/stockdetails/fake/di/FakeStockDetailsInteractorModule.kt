package com.orcchg.yandexcontest.stockdetails.fake.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsInteractor
import com.orcchg.yandexcontest.stockdetails.fake.FakeStockDetailsInteractor
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
@PublishedWithReasonableAlternatives(binding = StockDetailsInteractor::class)
interface FakeStockDetailsInteractorModule {

    @Binds
    @Reusable
    fun interactor(impl: FakeStockDetailsInteractor): StockDetailsInteractor
}
