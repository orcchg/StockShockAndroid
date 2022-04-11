package com.orcchg.yandexcontest.core.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.orcchg.yandexcontest.core.context.api.ApplicationContext
import com.orcchg.yandexcontest.core.network.interceptor.EncodingInterceptor
import com.orcchg.yandexcontest.coredi.InternalBindings
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module(includes = [ParserModule::class])
@InternalBindings
@Suppress("Unused")
internal object NetworkModule {

    @Provides
    fun chuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun stethoInterceptor(): StethoInterceptor = StethoInterceptor()

    @Provides
    @Reusable
    fun okHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        encodingInterceptor: EncodingInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        stethoInterceptor: StethoInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(encodingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(stethoInterceptor)
            .build()
}
