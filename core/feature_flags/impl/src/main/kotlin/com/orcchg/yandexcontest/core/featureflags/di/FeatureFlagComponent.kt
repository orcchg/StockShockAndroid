package com.orcchg.yandexcontest.core.featureflags.di

import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagApi
import dagger.Component

@Component(modules = [FeatureFlagModule::class])
interface FeatureFlagComponent : FeatureFlagApi
