package com.orcchg.yandexcontest.stockdetails.di

import com.orcchg.yandexcontest.core.schedulers.api.SchedulerApi
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import com.orcchg.yandexcontest.stockdetails.data.api.StockDetailsDataApi
import dagger.Component

@Component(
    modules = [
        StockDetailsInteractorModule::class
    ],
    dependencies = [
        SchedulerApi::class,
        StockDetailsDataApi::class
    ]
)
interface StockDetailsFeatureComponent : StockDetailsFeatureApi {

    @Component.Factory
    interface Factory {
        fun create(
            schedulerApi: SchedulerApi,
            stockDetailsDataApi: StockDetailsDataApi
        ): StockDetailsFeatureComponent
    }
}
