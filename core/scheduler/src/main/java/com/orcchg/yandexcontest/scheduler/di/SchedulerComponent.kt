package com.orcchg.yandexcontest.scheduler.di

import com.orcchg.yandexcontest.scheduler.api.di.SchedulerApi
import dagger.Component

@Component(modules = [SchedulersModule::class])
interface SchedulerComponent : SchedulerApi
