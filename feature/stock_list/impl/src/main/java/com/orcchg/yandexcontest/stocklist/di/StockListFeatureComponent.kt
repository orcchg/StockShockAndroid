package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagApi
import com.orcchg.yandexcontest.core.network.api.NetworkApi
import com.orcchg.yandexcontest.scheduler.api.SchedulerApi
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.Component

@Component(
    modules = [
        StockListInteractorModule::class
    ],
    dependencies = [
        ContextApi::class,
        FeatureFlagApi::class,
        NetworkApi::class,
        SchedulerApi::class
    ]
)
interface StockListFeatureComponent : StockListFeatureApi {

    @Component.Factory
    interface Factory {
        fun create(
            contextApi: ContextApi,
            featureFlagApi: FeatureFlagApi,
            networkApi: NetworkApi,
            schedulerApi: SchedulerApi
        ): StockListFeatureComponent
    }
}
