package com.orcchg.yandexcontest.fake.di

import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.Component

@Component(modules = [FakeStockListInteractorModule::class])
interface FakeStockListFeatureComponent : StockListFeatureApi
