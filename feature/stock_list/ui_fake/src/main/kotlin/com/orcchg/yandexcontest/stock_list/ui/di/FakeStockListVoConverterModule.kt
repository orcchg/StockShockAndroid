package com.orcchg.yandexcontest.stock_list.ui.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.stock_list.ui.convert.FakeLogoResByTickerSupplier
import com.orcchg.yandexcontest.util.ResourceSupplier
import dagger.Binds
import dagger.Module

@Module
@PublishedWithReasonableAlternatives(binding = ResourceSupplier::class)
interface FakeStockListVoConverterModule {

    @Binds
    fun issuerLogoResSupplier(impl: FakeLogoResByTickerSupplier): ResourceSupplier
}
