package com.orcchg.yandexcontest.di

import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.network.DaggerNetworkComponent
import com.orcchg.yandexcontest.network.api.NetworkApi
import com.orcchg.yandexcontest.scheduler.api.di.SchedulerApi
import com.orcchg.yandexcontest.scheduler.di.DaggerSchedulerComponent
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
object CoreApis {

    @Provides
    @IntoMap
    @ClassKey(NetworkApi::class)
    fun networkApi(): Api = DaggerNetworkComponent.create()

    @Provides
    @IntoMap
    @ClassKey(SchedulerApi::class)
    fun schedulerApi(): Api = DaggerSchedulerComponent.create()
}
