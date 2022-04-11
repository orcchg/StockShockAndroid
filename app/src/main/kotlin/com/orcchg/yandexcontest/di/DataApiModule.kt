package com.orcchg.yandexcontest.di

import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.coredi.get
import com.orcchg.yandexcontest.stockdetails.data.api.StockDetailsDataApi
import com.orcchg.yandexcontest.stockdetails.data.wiring.DaggerStockDetailsDataComponent
import com.orcchg.yandexcontest.stocklist.data.api.StockListDataApi
import com.orcchg.yandexcontest.stocklist.data.wiring.DaggerStockListDataComponent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(includes = [DataLibsModule::class])
interface DataApiModule {

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(StockDetailsDataApi::class)
    @DataApis
    fun stockDetailsDataApi(api: StockDetailsDataApi): Api

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(StockListDataApi::class)
    @DataApis
    fun stockListDataApi(api: StockListDataApi): Api
}

@Module
internal object DataLibsModule {

    @Provides
    @Reusable
    fun stockDetailsDataApi(
        @CoreApis coreApis: Map<Class<*>, @JvmSuppressWildcards Api>
    ): StockDetailsDataApi =
        DaggerStockDetailsDataComponent.factory()
            .create(
                contextApi = coreApis.get(),
                featureFlagApi = coreApis.get(),
                networkApi = coreApis.get(),
                schedulerApi = coreApis.get()
            )

    @Provides
    @Reusable
    fun stockListDataApi(
        @CoreApis coreApis: Map<Class<*>, @JvmSuppressWildcards Api>
    ): StockListDataApi =
        DaggerStockListDataComponent.factory()
            .create(
                contextApi = coreApis.get(),
                featureFlagApi = coreApis.get(),
                networkApi = coreApis.get(),
                schedulerApi = coreApis.get()
            )
}
