package com.orcchg.yandexcontest.core.featureflags.api

interface FeatureFlagManager {

    fun isRealTimeQuotesEnabled(): Boolean

    fun getStockIndex(): String
}
