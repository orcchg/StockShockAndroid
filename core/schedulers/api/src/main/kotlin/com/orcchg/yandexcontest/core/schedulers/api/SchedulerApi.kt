package com.orcchg.yandexcontest.scheduler.api

import com.orcchg.yandexcontest.coredi.Api

interface SchedulerApi : Api {

    fun schedulersFactory(): SchedulersFactory
}
