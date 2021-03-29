package com.orcchg.yandexcontest.core.featureflags

import YandexMobileContest2021.core.feature_flags.BuildConfig
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagManager
import javax.inject.Inject

class FeatureFlagManagerImpl @Inject constructor() : FeatureFlagManager {

    override fun isRealTimeQuotesEnabled(): Boolean =
        BuildConfig.FEATURE_FLAG_REAL_TIME_QUOTES_ENABLED

    override fun getStockIndex(): String = BuildConfig.DEFAULT_STOCK_INDEX
}
