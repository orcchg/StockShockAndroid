package com.orcchg.yandexcontest.core.featureflags.di

import com.orcchg.yandexcontest.core.featureflags.FeatureFlagManagerImpl
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagManager
import com.orcchg.yandexcontest.coredi.PublishedNoReasonableAlternatives
import dagger.Binds
import dagger.Module

@Module
@PublishedNoReasonableAlternatives
interface FeatureFlagModule {

    @Binds
    fun featureFlagManager(impl: FeatureFlagManagerImpl): FeatureFlagManager
}
