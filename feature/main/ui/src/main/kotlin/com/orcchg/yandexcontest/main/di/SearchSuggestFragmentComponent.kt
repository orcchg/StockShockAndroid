package com.orcchg.yandexcontest.main.di

import com.orcchg.yandexcontest.main.ui.SearchSuggestFragment
import com.orcchg.yandexcontest.search.api.SearchFeatureApi
import dagger.Component

@Component(dependencies = [SearchFeatureApi::class])
internal interface SearchSuggestFragmentComponent {

    fun inject(target: SearchSuggestFragment)

    @Component.Factory
    interface Factory {
        fun create(featureApi: SearchFeatureApi): SearchSuggestFragmentComponent
    }
}
