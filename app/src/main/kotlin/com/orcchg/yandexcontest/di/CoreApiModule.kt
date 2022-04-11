package com.orcchg.yandexcontest.di

import android.app.Application
import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.core.context.impl.DaggerContextComponent
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagApi
import com.orcchg.yandexcontest.core.featureflags.di.DaggerFeatureFlagComponent
import com.orcchg.yandexcontest.core.network.api.NetworkApi
import com.orcchg.yandexcontest.core.network.di.DaggerNetworkComponent
import com.orcchg.yandexcontest.core.parser.di.ParserApi
import com.orcchg.yandexcontest.core.parser.di.DaggerParserComponent
import com.orcchg.yandexcontest.core.schedulers.impl.di.DaggerSchedulerComponent
import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.core.schedulers.api.SchedulerApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(includes = [CoreLibsModule::class])
interface CoreApiModule {

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(ContextApi::class)
    @CoreApis
    fun contextApi(api: ContextApi): Api

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(FeatureFlagApi::class)
    @CoreApis
    fun featureFlagApi(api: FeatureFlagApi): Api

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(NetworkApi::class)
    @CoreApis
    fun networkApi(api: NetworkApi): Api

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(ParserApi::class)
    @CoreApis
    fun parserApi(api: ParserApi): Api

    @Binds
    @Reusable
    @IntoMap
    @ClassKey(SchedulerApi::class)
    @CoreApis
    fun schedulerApi(api: SchedulerApi): Api
}

@Module
class CoreLibsModule(private val application: Application) {

    private val contextApi: ContextApi by lazy(LazyThreadSafetyMode.NONE) {
        DaggerContextComponent.factory().create(application, application.applicationContext)
    }

    @Provides
    @Reusable
    fun contextApi(): ContextApi =
        contextApi

    @Provides
    @Reusable
    fun featureFlagApi(): FeatureFlagApi =
        DaggerFeatureFlagComponent.create()

    @Provides
    @Reusable
    fun networkApi(
        contextApi: ContextApi,
        parserApi: ParserApi
    ): NetworkApi =
        DaggerNetworkComponent.factory().create(
            contextApi = contextApi,
            parserApi = parserApi
        )

    @Provides
    @Reusable
    fun parserApi(): ParserApi =
        DaggerParserComponent.create()

    @Provides
    @Reusable
    fun schedulerApi(): SchedulerApi =
        DaggerSchedulerComponent.create()
}
