package com.orcchg.yandexcontest.di

import android.app.Application
import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.core.context.impl.DaggerContextComponent
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagApi
import com.orcchg.yandexcontest.core.featureflags.di.DaggerFeatureFlagComponent
import com.orcchg.yandexcontest.core.network.api.NetworkApi
import com.orcchg.yandexcontest.core.network.di.DaggerNetworkComponent
import com.orcchg.yandexcontest.core.schedulers.impl.di.DaggerSchedulerComponent
import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.scheduler.api.SchedulerApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
class CoreApiModule(private val application: Application) {

    private val contextApi: ContextApi by lazy(LazyThreadSafetyMode.NONE) {
        DaggerContextComponent.factory().create(application, application.applicationContext)
    }

    @Provides
    @IntoMap
    @ClassKey(ContextApi::class)
    @CoreApis
    fun contextApi(): Api = contextApi

    @Provides
    @IntoMap
    @ClassKey(FeatureFlagApi::class)
    @CoreApis
    fun featureFlagApi(): Api = DaggerFeatureFlagComponent.create()

    @Provides
    @IntoMap
    @ClassKey(NetworkApi::class)
    @CoreApis
    fun networkApi(): Api = DaggerNetworkComponent.factory().create(contextApi)

    @Provides
    @IntoMap
    @ClassKey(SchedulerApi::class)
    @CoreApis
    fun schedulerApi(): Api = DaggerSchedulerComponent.create()
}
