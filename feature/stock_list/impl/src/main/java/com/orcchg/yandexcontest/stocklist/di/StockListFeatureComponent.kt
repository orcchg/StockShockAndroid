package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.network.api.NetworkApi
import com.orcchg.yandexcontest.scheduler.api.di.SchedulerApi
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.Component

@Component(
    modules = [
        StockListInteractorModule::class
    ],
    dependencies = [
        NetworkApi::class,
        SchedulerApi::class
    ]
)
interface StockListFeatureComponent : StockListFeatureApi {

    @Component.Factory
    interface Factory {
        fun create(
            networkApi: NetworkApi,
            schedulerApi: SchedulerApi
        ): StockListFeatureComponent
    }
}
