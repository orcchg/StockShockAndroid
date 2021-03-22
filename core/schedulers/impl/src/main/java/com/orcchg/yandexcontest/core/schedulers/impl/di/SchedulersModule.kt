package com.orcchg.yandexcontest.core.schedulers.impl.di

import com.orcchg.yandexcontest.coredi.PublishedNoReasonableAlternatives
import com.orcchg.yandexcontest.scheduler.SchedulersFactoryImpl
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
@PublishedNoReasonableAlternatives
interface SchedulersModule {

    @Binds
    @Reusable
    fun schedulersFactory(impl: SchedulersFactoryImpl): SchedulersFactory
}
