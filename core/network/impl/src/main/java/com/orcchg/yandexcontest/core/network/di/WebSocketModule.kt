package com.orcchg.yandexcontest.core.network.di

import android.app.Application
import com.orcchg.yandexcontest.core.network.interceptor.API_KEY
import com.orcchg.yandexcontest.coredi.PublishedNoReasonableAlternatives
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.ExponentialWithJitterBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient

@Suppress("Unused")
@Module(includes = [CloudModule::class])
@PublishedNoReasonableAlternatives
internal object WebSocketModule {

    @Provides
    @Reusable
    fun scarlet(client: OkHttpClient, moshi: Moshi, app: Application): Scarlet =
        Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("wss://ws.finnhub.io?token=$API_KEY"))
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .backoffStrategy(ExponentialWithJitterBackoffStrategy(50L, 1000L))
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .build()
}
