package com.orcchg.yandexcontest.core.context.api

import android.content.Context
import com.orcchg.yandexcontest.coredi.Api

interface ContextApi : Api {

    @ApplicationContext
    fun context(): Context
}
