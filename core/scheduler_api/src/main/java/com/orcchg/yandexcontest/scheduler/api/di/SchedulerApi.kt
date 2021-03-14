package com.orcchg.yandexcontest.scheduler.api.di

import com.orcchg.yandexcontest.coredi.Api
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory

interface SchedulerApi : Api {

    fun schedulersFactory(): SchedulersFactory
}
