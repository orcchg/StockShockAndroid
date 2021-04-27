package com.orcchg.yandexcontest.stockdetails.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.stockdetails.StockDetailsInteractorImpl
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsInteractor
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
@PublishedWithReasonableAlternatives(binding = StockDetailsInteractor::class)
interface StockDetailsInteractorModule {

    @Binds
    @Reusable
    fun interactor(impl: StockDetailsInteractorImpl): StockDetailsInteractor
}
