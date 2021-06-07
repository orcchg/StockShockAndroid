package com.orcchg.yandexcontest.core.schedulers.api

import com.orcchg.yandexcontest.coredi.Api

interface SchedulerApi : Api {

    fun schedulersFactory(): SchedulersFactory
}
