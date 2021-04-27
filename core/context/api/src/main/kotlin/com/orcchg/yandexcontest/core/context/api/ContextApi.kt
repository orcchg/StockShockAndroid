package com.orcchg.yandexcontest.core.context.api

import android.app.Application
import android.content.Context
import com.orcchg.yandexcontest.coredi.Api

interface ContextApi : Api {

    fun application(): Application

    @ApplicationContext
    fun context(): Context
}
