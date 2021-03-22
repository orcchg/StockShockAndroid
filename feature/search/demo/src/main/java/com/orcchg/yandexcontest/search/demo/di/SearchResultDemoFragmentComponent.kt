package com.orcchg.yandexcontest.search.demo.di

import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.search.demo.ui.SearchResultDemoFragment
import com.orcchg.yandexcontest.stock_list.ui.di.FakeStockListVoConverterModule
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import com.orcchg.yandexcontest.stocklist.api.StockSelectionSupplier
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Component(
    modules = [
        FakeStockListVoConverterModule::class,
        SearchResultDemoFragmentComponent.Support::class
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
            @BindsInstance @Named("initialQuery") initialQuery: String,
            featureApi: StockListFeatureApi
        ): SearchResultDemoFragmentComponent
    }

    @Module
    object Support {
        @Provides
        fun stockSelectionSupplier() = StockSelectionSupplier { StockSelection.ALL }
    }
}
