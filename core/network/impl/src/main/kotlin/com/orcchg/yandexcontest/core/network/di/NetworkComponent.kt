package com.orcchg.yandexcontest.core.network.di

import com.orcchg.yandexcontest.core.context.api.ContextApi
import com.orcchg.yandexcontest.core.network.api.NetworkApi
import dagger.Component

@Component(
    modules = [
        RestModule::class,
        WebSocketModule::class
    ],
    dependencies = [
        ContextApi::class
    ]
)
interface NetworkComponent : NetworkApi {

    @Component.Factory
    interface Factory {
        fun create(contextApi: ContextApi): NetworkComponent
    }
}
