package com.orcchg.yandexcontest.stockdetails.main.di

import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import com.orcchg.yandexcontest.stockdetails.api.Ticker
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsChartFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [StockDetailsFeatureApi::class])
internal interface StockDetailsChartFragmentComponent {

    fun inject(target: StockDetailsChartFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Ticker ticker: String,
            featureApi: StockDetailsFeatureApi
        ): StockDetailsChartFragmentComponent
    }
}
