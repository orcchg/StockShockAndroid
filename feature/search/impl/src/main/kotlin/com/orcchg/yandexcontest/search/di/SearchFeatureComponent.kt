package com.orcchg.yandexcontest.search.di

import com.orcchg.yandexcontest.core.schedulers.api.SchedulerApi
import com.orcchg.yandexcontest.search.api.SearchFeatureApi
import dagger.Component

@Component(
    modules = [
        SearchInteractorModule::class
    ],
    dependencies = [
        SchedulerApi::class
    ]
)
interface SearchFeatureComponent : SearchFeatureApi {

    @Component.Factory
    interface Factory {
        fun create(schedulerApi: SchedulerApi): SearchFeatureComponent
    }
}
