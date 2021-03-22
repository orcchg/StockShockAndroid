package com.orcchg.yandexcontest.core.context.impl

import android.content.Context
import com.orcchg.yandexcontest.core.context.api.ApplicationContext
import com.orcchg.yandexcontest.core.context.api.ContextApi
import dagger.BindsInstance
import dagger.Component

@Component
interface ContextComponent : ContextApi {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @ApplicationContext context: Context): ContextComponent
    }
}
