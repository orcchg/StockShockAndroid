package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.network.api.NetworkApi
import com.orcchg.yandexcontest.scheduler.api.di.SchedulerApi
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.Component

@Component(
    modules = [
        StockListInteractorModule::class
    ],
    dependencies = [
        ContextApi::class,
        NetworkApi::class,
        SchedulerApi::class
    ]
)
interface StockListFeatureComponent : StockListFeatureApi {

    @Component.Factory
    interface Factory {
        fun create(
            contextApi: ContextApi,
            networkApi: NetworkApi,
            schedulerApi: SchedulerApi
        ): StockListFeatureComponent
    }
}
