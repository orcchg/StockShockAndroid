package com.orcchg.yandexcontest.core.network.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.orcchg.yandexcontest.core.network.interceptor.AuthHeaderInterceptor
import com.orcchg.yandexcontest.core.network.interceptor.EncodingInterceptor
import com.orcchg.yandexcontest.core.network.parser.BigDecimalAdapter
import com.orcchg.yandexcontest.core.network.parser.MoshiAdapters
import com.orcchg.yandexcontest.coredi.InternalBindings
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InternalBindings
@Suppress("Unused")
internal object CloudModule {

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun stethoInterceptor(): StethoInterceptor = StethoInterceptor()

    @Provides
    @Reusable
    fun moshi(): Moshi =
        Moshi.Builder()
            .add(BigDecimalAdapter)
            .add(MoshiAdapters)
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Reusable
    fun okHttpClient(
        authHeaderInterceptor: AuthHeaderInterceptor,
        encodingInterceptor: EncodingInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        stethoInterceptor: StethoInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authHeaderInterceptor)
            .addInterceptor(encodingInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(stethoInterceptor)
            .build()
}
