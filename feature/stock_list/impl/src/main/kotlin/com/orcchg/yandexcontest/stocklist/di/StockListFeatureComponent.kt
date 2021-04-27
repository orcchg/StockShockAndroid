package com.orcchg.yandexcontest.stocklist.di

import com.orcchg.yandexcontest.scheduler.api.SchedulerApi
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import com.orcchg.yandexcontest.stocklist.data.api.StockListDataApi
import dagger.Component

@Component(
    modules = [
        StockListInteractorModule::class
    ],
    dependencies = [
        SchedulerApi::class,
        StockListDataApi::class
    ]
)
interface StockListFeatureComponent : StockListFeatureApi {

    @Component.Factory
    interface Factory {
        fun create(
            schedulerApi: SchedulerApi,
            stockListDataApi: StockListDataApi
        ): StockListFeatureComponent
    }
}
