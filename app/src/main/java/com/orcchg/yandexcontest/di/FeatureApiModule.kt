package com.orcchg.yandexcontest.di

import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.coredi.get
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import com.orcchg.yandexcontest.stocklist.di.DaggerStockListFeatureComponent
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
object FeatureApiModule {

    @Provides
    @IntoMap
    @ClassKey(StockListFeatureApi::class)
    @FeatureApis
    fun stockListFeatureApi(@CoreApis coreApis: Map<Class<*>, @JvmSuppressWildcards Api>): Api =
        DaggerStockListFeatureComponent.factory()
            .create(
                contextApi = coreApis.get(),
                networkApi = coreApis.get(),
                schedulerApi = coreApis.get()
            )
}
