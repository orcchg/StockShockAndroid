package com.orcchg.yandexcontest.search.demo.di

import com.orcchg.yandexcontest.search.demo.ui.SearchResultDemoFragment
import com.orcchg.yandexcontest.stock_list.ui.di.FakeStockListVoConverterModule
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.Component

@Component(
    modules = [
        FakeStockListVoConverterModule::class
    ],
    dependencies = [
        StockListFeatureApi::class
    ]
)
internal interface SearchResultDemoFragmentComponent {

    fun inject(target: SearchResultDemoFragment)

    @Component.Factory
    interface Factory {
        fun create(
            featureApi: StockListFeatureApi
        ): SearchResultDemoFragmentComponent
    }
}
