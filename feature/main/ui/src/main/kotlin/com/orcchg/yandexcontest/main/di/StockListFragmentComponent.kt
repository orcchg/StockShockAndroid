package com.orcchg.yandexcontest.main.di

import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.main.ui.StockListFragment
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import com.orcchg.yandexcontest.stocklist.ui.di.StockListVoConverterModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        StockListVoConverterModule::class
    ],
    dependencies = [
        StockListFeatureApi::class
    ]
)
internal interface StockListFragmentComponent {

    fun inject(target: StockListFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance stockSelection: StockSelection,
            featureApi: StockListFeatureApi
        ): StockListFragmentComponent
    }
}
