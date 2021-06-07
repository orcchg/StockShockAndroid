package com.orcchg.yandexcontest.stocklist.data.wiring

import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagApi
import com.orcchg.yandexcontest.core.network.api.NetworkApi
import com.orcchg.yandexcontest.core.schedulers.api.SchedulerApi
import com.orcchg.yandexcontest.stocklist.data.api.StockListDataApi
import dagger.Component

@Component(
    modules = [
        StockListDataModule::class
    ],
    dependencies = [
        ContextApi::class,
        FeatureFlagApi::class,
        NetworkApi::class,
        SchedulerApi::class
    ]
)
interface StockListDataComponent : StockListDataApi {

    @Component.Factory
    interface Factory {
        fun create(
            contextApi: ContextApi,
            featureFlagApi: FeatureFlagApi,
            networkApi: NetworkApi,
            schedulerApi: SchedulerApi
        ): StockListDataComponent
    }
}
