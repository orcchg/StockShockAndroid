package com.orcchg.yandexcontest.core.schedulers.impl.di

import com.orcchg.yandexcontest.scheduler.api.SchedulerApi
import dagger.Component

@Component(modules = [SchedulersModule::class])
interface SchedulerComponent : SchedulerApi
