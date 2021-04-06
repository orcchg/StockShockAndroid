package com.orcchg.yandexcontest.stockdetails.demo.di

import com.orcchg.yandexcontest.stockdetails.api.StockDetailsFeatureApi
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsDemoActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@Component(
    dependencies = [
        StockDetailsFeatureApi::class
    ]
)
internal interface StockDetailsDemoActivityComponent {

    fun inject(target: StockDetailsDemoActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("ticker") ticker: String,
            featureApi: StockDetailsFeatureApi
        ): StockDetailsDemoActivityComponent
    }
}
