package com.orcchg.yandexcontest.scheduler.di

import com.orcchg.yandexcontest.scheduler.SchedulersFactoryImpl
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
interface SchedulersModule {

    @Binds
    @Reusable
    fun schedulersFactory(impl: SchedulersFactoryImpl): SchedulersFactory
}
