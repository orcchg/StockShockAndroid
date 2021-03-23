package com.orcchg.yandexcontest.search.di

import com.orcchg.yandexcontest.coredi.PublishedWithReasonableAlternatives
import com.orcchg.yandexcontest.search.SearchInteractorImpl
import com.orcchg.yandexcontest.search.api.SearchInteractor
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(includes = [SearchDataModule::class])
@PublishedWithReasonableAlternatives(binding = SearchInteractor::class)
interface SearchInteractorModule {

    @Binds
    @Reusable
    fun interactor(impl: SearchInteractorImpl): SearchInteractor
}
