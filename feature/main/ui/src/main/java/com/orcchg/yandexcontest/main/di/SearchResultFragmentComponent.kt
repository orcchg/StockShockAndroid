package com.orcchg.yandexcontest.main.di

import com.orcchg.yandexcontest.main.ui.SearchResultFragment
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import com.orcchg.yandexcontest.stocklist.api.StockSelectionSupplier
import com.orcchg.yandexcontest.stocklist.StockSelectionLocalSupplier
import com.orcchg.yandexcontest.stocklist.di.StockListVoConverterModule
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Named

@Component(
    modules = [
        StockListVoConverterModule::class,
        SearchResultFragmentComponent.Support::class
    ],
    dependencies = [
        StockListFeatureApi::class
    ]
)
internal interface SearchResultFragmentComponent {

    fun inject(target: SearchResultFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("initialQuery") initialQuery: String,
            featureApi: StockListFeatureApi
        ): SearchResultFragmentComponent
    }

    @Module
    interface Support {
        @Binds
        fun stockSelectionSupplier(impl: StockSelectionLocalSupplier): StockSelectionSupplier
    }
}
