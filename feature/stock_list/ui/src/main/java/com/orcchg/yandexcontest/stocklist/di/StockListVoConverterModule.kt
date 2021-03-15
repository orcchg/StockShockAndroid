package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.stocklist.convert.RealLogoResByTickerSupplier
import com.orcchg.yandexcontest.util.ResourceSupplier
import dagger.Binds
import dagger.Module

@Module
@PublishedWithReasonableAlternatives(binding = ResourceSupplier::class)
interface StockListVoConverterModule {

    @Binds
    fun issuerLogoResSupplier(impl: RealLogoResByTickerSupplier): ResourceSupplier
}
