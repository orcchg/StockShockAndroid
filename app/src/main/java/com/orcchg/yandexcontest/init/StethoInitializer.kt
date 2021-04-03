package com.orcchg.yandexcontest.init

import android.content.Context
import androidx.startup.Initializer
import com.facebook.stetho.Stetho

class StethoInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Stetho.initializeWithDefaults(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        emptyList()
}
