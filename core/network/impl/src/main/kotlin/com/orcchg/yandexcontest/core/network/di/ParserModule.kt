package com.orcchg.yandexcontest.core.network.di

import com.orcchg.yandexcontest.core.network.parser.MoshiAdapters
import com.orcchg.yandexcontest.core.network.parser.NetworkMoshi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ParserModule {

    @Provides
    @Reusable
    @NetworkMoshi
    fun moshi(moshi: Moshi): Moshi =
        moshi.newBuilder()
            .add(MoshiAdapters)
            .build()
}
