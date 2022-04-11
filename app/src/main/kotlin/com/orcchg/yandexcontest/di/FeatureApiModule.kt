package com.orcchg.yandexcontest.di

import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.coredi.get
import com.orcchg.yandexcontest.search.api.SearchFeatureApi
import com.orcchg.yandexcontest.search.di.DaggerSearchFeatureComponent
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import com.orcchg.yandexcontest.stockdetails.di.DaggerStockDetailsFeatureComponent
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import com.orcchg.yandexcontest.stocklist.di.DaggerStockListFeatureComponent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(includes = [FeaturesModule::class])
interface FeatureApiModule {

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(SearchFeatureApi::class)
    @FeatureApis
    fun searchFeatureApi(api: SearchFeatureApi): Api

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(StockDetailsFeatureApi::class)
    @FeatureApis
    fun stockDetailsFeatureApi(api: StockDetailsFeatureApi): Api

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(StockListFeatureApi::class)
    @FeatureApis
    fun stockListFeatureApi(api: StockListFeatureApi): Api
}

@Module
internal object FeaturesModule {

    @Provides
    @Reusable
    fun searchFeatureApi(
        @CoreApis coreApis: Map<Class<*>, @JvmSuppressWildcards Api>
    ): SearchFeatureApi =
        DaggerSearchFeatureComponent.factory()
            .create(schedulerApi = coreApis.get())

    @Provides
    @Reusable
    fun stockDetailsFeatureApi(
        @CoreApis coreApis: Map<Class<*>, @JvmSuppressWildcards Api>,
        @DataApis dataApis: Map<Class<*>, @JvmSuppressWildcards Api>
    ): StockDetailsFeatureApi =
        DaggerStockDetailsFeatureComponent.factory()
            .create(
                schedulerApi = coreApis.get(),
                stockDetailsDataApi = dataApis.get()
            )

    @Provides
    @Reusable
    fun stockListFeatureApi(
        @CoreApis coreApis: Map<Class<*>, @JvmSuppressWildcards Api>,
        @DataApis dataApis: Map<Class<*>, @JvmSuppressWildcards Api>
    ): StockListFeatureApi =
        DaggerStockListFeatureComponent.factory()
            .create(
                schedulerApi = coreApis.get(),
                stockListDataApi = dataApis.get()
            )
}
