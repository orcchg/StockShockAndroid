package com.orcchg.yandexcontest.di

import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.coredi.get
import com.orcchg.yandexcontest.stocklist.data.api.StockListDataApi
import com.orcchg.yandexcontest.stocklist.data.wiring.DaggerStockListDataComponent
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
class DataApiModule {

    @Provides
    @IntoMap
    @ClassKey(StockListDataApi::class)
    @DataApis
    fun stockListDataApi(@CoreApis coreApis: Map<Class<*>, @JvmSuppressWildcards Api>): Api =
        DaggerStockListDataComponent.factory()
            .create(
                contextApi = coreApis.get(),
                featureFlagApi = coreApis.get(),
                networkApi = coreApis.get(),
                schedulerApi = coreApis.get()
            )
}
