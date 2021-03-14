package com.orcchg.yandexcontest.di

import com.orcchg.yandexcontest.App
import com.orcchg.yandexcontest.coredi.Api
import dagger.Component

@Component(
    modules = [
        CoreApis::class
    ]
)
interface AppComponent {

    @JvmSuppressWildcards
    val featuresMap: Map<Class<*>, Api>

    fun inject(target: App)
}
