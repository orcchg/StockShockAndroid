package com.orcchg.yandexcontest.di

import android.content.Context
import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.core.context.impl.DaggerContextComponent
import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.core.network.di.DaggerNetworkComponent
import com.orcchg.yandexcontest.core.network.api.NetworkApi
import com.orcchg.yandexcontest.core.schedulers.impl.di.DaggerSchedulerComponent
import com.orcchg.yandexcontest.scheduler.api.SchedulerApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
class CoreApiModule(private val context: Context) {

    @Provides
    @IntoMap
    @ClassKey(ContextApi::class)
    @CoreApis
    fun contextApi(): Api = DaggerContextComponent.factory().create(context)

    @Provides
    @IntoMap
    @ClassKey(NetworkApi::class)
    @CoreApis
    fun networkApi(): Api = DaggerNetworkComponent.create()

    @Provides
    @IntoMap
    @ClassKey(SchedulerApi::class)
    @CoreApis
    fun schedulerApi(): Api = DaggerSchedulerComponent.create()
}
