package com.orcchg.yandexcontest

import android.app.Application
import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.coredi.ApiContainer
import com.orcchg.yandexcontest.di.CoreLibsModule
import com.orcchg.yandexcontest.di.DaggerAppComponent
import com.orcchg.yandexcontest.di.FeatureApis
import javax.inject.Inject

class App : Application(), ApiContainer {

    @Inject @FeatureApis lateinit var featuresMap: Map<Class<*>, @JvmSuppressWildcards Api>

    @Suppress("Unchecked_Cast")
    override fun <T> getFeature(key: Class<T>): T = featuresMap[key] as T

    override fun onCreate() {
        DaggerAppComponent.factory()
            .create(CoreLibsModule(application = this))
            .inject(this)
        super.onCreate()
    }
}
