package com.orcchg.yandexcontest.di

import com.orcchg.yandexcontest.App
import com.orcchg.yandexcontest.coredi.Api
import dagger.Component

@Component(
    modules = [
        CoreApiModule::class,
        FeatureApiModule::class
    ]
)
interface AppComponent {

    @FeatureApis
    fun featuresMap(): Map<Class<*>, @JvmSuppressWildcards Api>

    fun inject(target: App)

    @Component.Factory
    interface Factory {
        fun create(coreApiModule: CoreApiModule): AppComponent
    }
}
