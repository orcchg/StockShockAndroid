package com.orcchg.yandexcontest.stockdetails.data.wiring

import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagApi
import com.orcchg.yandexcontest.core.network.api.NetworkApi
import com.orcchg.yandexcontest.core.schedulers.api.SchedulerApi
import com.orcchg.yandexcontest.stockdetails.data.api.StockDetailsDataApi
import dagger.Component

@Component(
    modules = [
        StockDetailsDataModule::class
    ],
    dependencies = [
        ContextApi::class,
        FeatureFlagApi::class,
        NetworkApi::class,
        SchedulerApi::class
    ]
)
interface StockDetailsDataComponent : StockDetailsDataApi {

    @Component.Factory
    interface Factory {
        fun create(
            contextApi: ContextApi,
            featureFlagApi: FeatureFlagApi,
            networkApi: NetworkApi,
            schedulerApi: SchedulerApi
        ): StockDetailsDataComponent
    }
}
