package com.orcchg.yandexcontest.network

import com.orcchg.yandexcontest.network.api.NetworkApi
import dagger.Component

@Component(modules = [CloudModule::class])
interface NetworkComponent : NetworkApi
