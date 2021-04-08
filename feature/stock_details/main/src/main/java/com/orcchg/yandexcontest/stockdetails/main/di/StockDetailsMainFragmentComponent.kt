package com.orcchg.yandexcontest.stockdetails.main.di

import androidx.fragment.app.Fragment
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import com.orcchg.yandexcontest.stockdetails.api.Ticker
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsMainFragment
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [
        StockDetailsFeatureApi::class,
        StockListFeatureApi::class
    ]
)
internal interface StockDetailsMainFragmentComponent {

    fun inject(target: StockDetailsMainFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance fragment: Fragment,
            @BindsInstance @Ticker ticker: String,
            featureApi: StockDetailsFeatureApi,
            stockListFeatureApi: StockListFeatureApi
        ): StockDetailsMainFragmentComponent
    }
}
