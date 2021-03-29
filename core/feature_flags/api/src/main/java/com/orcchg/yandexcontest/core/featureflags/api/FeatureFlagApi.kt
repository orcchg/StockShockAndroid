package com.orcchg.yandexcontest.core.featureflags.api

import com.orcchg.yandexcontest.coredi.Api

interface FeatureFlagApi : Api {

    fun featureFlagManager(): FeatureFlagManager
}
