package com.orcchg.yandexcontest.core.network.di

import com.orcchg.yandexcontest.core.network.parser.NetworkMoshi
import com.orcchg.yandexcontest.coredi.PublishedNoReasonableAlternatives
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Suppress("Unused")
@Module(includes = [NetworkModule::class])
@PublishedNoReasonableAlternatives
internal object RestModule {

    @Provides
    @Reusable
    fun retrofit(client: OkHttpClient, @NetworkMoshi moshi: Moshi): Retrofit.Builder =
        Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
}
