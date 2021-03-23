package com.orcchg.yandexcontest.search.di

import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.search.data.SearchRepositoryImpl
import com.orcchg.yandexcontest.search.domain.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
@InternalBindings
interface SearchDataModule {

    @Binds
    @Reusable
    fun repository(impl: SearchRepositoryImpl): SearchRepository
}
