package com.orcchg.yandexcontest.stockdetails.fake.di

import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import dagger.Component

@Component(modules = [FakeStockDetailsInteractorModule::class])
interface FakeStockDetailsFeatureComponent : StockDetailsFeatureApi
