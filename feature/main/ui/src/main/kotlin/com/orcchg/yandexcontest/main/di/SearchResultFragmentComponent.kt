package com.orcchg.yandexcontest.main.di

import com.orcchg.yandexcontest.main.ui.SearchResultFragment
import com.orcchg.yandexcontest.search.api.SearchFeatureApi
import com.orcchg.yandexcontest.stocklist.api.StockListFeatureApi
import com.orcchg.yandexcontest.stocklist.ui.di.StockListVoConverterModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@Component(
    modules = [
        StockListVoConverterModule::class
    ],
    dependencies = [
        SearchFeatureApi::class,
        StockListFeatureApi::class
    ]
)
internal interface SearchResultFragmentComponent {

    fun inject(target: SearchResultFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("initialQuery") initialQuery: String,
            searchFeatureApi: SearchFeatureApi,
            stockListFeatureApi: StockListFeatureApi
        ): SearchResultFragmentComponent
    }
}
