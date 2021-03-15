package com.orcchg.yandexcontest.network

import com.orcchg.yandexcontest.network.api.NetworkApi
import dagger.Component

@Component(modules = [RestModule::class])
interface NetworkComponent : NetworkApi
