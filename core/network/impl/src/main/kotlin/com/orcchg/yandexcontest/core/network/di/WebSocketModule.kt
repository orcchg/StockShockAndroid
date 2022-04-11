package com.orcchg.yandexcontest.core.network.di

import android.app.Application
import com.orcchg.yandexcontest.core.network.parser.NetworkMoshi
import com.orcchg.yandexcontest.coredi.PublishedNoReasonableAlternatives
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Suppress("Unused")
@Module(includes = [NetworkModule::class])
@PublishedNoReasonableAlternatives
internal object WebSocketModule {

    @Provides
    @Reusable
    fun scarlet(@NetworkMoshi moshi: Moshi, app: Application): Scarlet.Builder =
        Scarlet.Builder()
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .backoffStrategy(ExponentialWithJitterBackoffStrategy(50L, 1000L))
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
}
