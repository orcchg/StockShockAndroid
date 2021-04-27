package com.orcchg.yandexcontest.main.demo.di

import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.main.demo.ui.StockListDemoFragment
import com.orcchg.yandexcontest.stock_list.ui.di.FakeStockListVoConverterModule
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        FakeStockListVoConverterModule::class
    ],
    dependencies = [
        StockListFeatureApi::class
    ]
)
internal interface StockListDemoFragmentComponent {

    fun inject(target: StockListDemoFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance stockSelection: StockSelection,
            featureApi: StockListFeatureApi
        ): StockListDemoFragmentComponent
    }
}
