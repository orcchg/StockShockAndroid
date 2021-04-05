package com.orcchg.yandexcontest.stockdetails.di

import com.orcchg.yandexcontest.scheduler.api.SchedulerApi
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import dagger.Component

@Component(
    modules = [
        StockDetailsInteractorModule::class
    ],
    dependencies = [
        SchedulerApi::class
        // TODO: data api
    ]
)
interface StockDetailsFeatureComponent : StockDetailsFeatureApi {

    @Component.Factory
    interface Factory {
        fun create(
            schedulerApi: SchedulerApi
            // TODO: data api
        ): StockDetailsFeatureComponent
    }
}
