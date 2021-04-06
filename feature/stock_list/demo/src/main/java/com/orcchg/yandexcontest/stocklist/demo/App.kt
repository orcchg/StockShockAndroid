package com.orcchg.yandexcontest.stocklist.demo

import android.app.Application
import timber.log.Timber

internal class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
