package com.orcchg.yandexcontest.core.network.di

import com.orcchg.yandexcontest.core.network.api.NetworkApi
import dagger.Component

@Component(
    modules = [
        RestModule::class,
        WebSocketModule::class
    ]
)
interface NetworkComponent : NetworkApi
