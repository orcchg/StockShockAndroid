package com.orcchg.yandexcontest.stockdetails.demo.di

import androidx.fragment.app.FragmentActivity
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsDemoActivity
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@Component(
    dependencies = [
        StockDetailsFeatureApi::class,
        StockListFeatureApi::class
    ]
)
internal interface StockDetailsDemoActivityComponent {

    fun inject(target: StockDetailsDemoActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: FragmentActivity,
            @BindsInstance @Named("ticker") ticker: String,
            featureApi: StockDetailsFeatureApi,
            stockListFeatureApi: StockListFeatureApi
        ): StockDetailsDemoActivityComponent
    }
}
