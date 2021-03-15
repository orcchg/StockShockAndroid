package com.orcchg.yandexcontest.stocklist.demo.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.stocklist.demo.convert.FakeLogoResByTickerSupplier
import com.orcchg.yandexcontest.util.ResourceSupplier
import dagger.Binds
import dagger.Module

@Module
@PublishedWithReasonableAlternatives(binding = ResourceSupplier::class)
interface FakeStockListVoConverterModule {

    @Binds
    fun issuerLogoResSupplier(impl: FakeLogoResByTickerSupplier): ResourceSupplier
}
