package com.orcchg.yandexcontest.stocklist.data

import android.annotation.SuppressLint
import com.orcchg.yandexcontest.core.network.api.WsSubscribeType
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.StockListSharedPrefs
import com.orcchg.yandexcontest.stocklist.data.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.StockListWebSocket
import com.orcchg.yandexcontest.stocklist.data.remote.convert.WsQuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.model.WsSubscribeEntity
import com.orcchg.yandexcontest.stocklist.domain.RealTimeStocksRepository
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class RealTimeStocksRepositoryImpl @Inject constructor(
    private val localIssuer: IssuerDao,
    private val issuerLocalConverter: IssuerDboConverter,
    private val webSocketCloud: StockListWebSocket,
    private val wsQuoteNetworkConverter: WsQuoteNetworkConverter,
    private val sharedPrefs: StockListSharedPrefs,
    schedulersFactory: SchedulersFactory
) : RealTimeStocksRepository {

    private val webSocketEvents = BehaviorSubject.create<WebSocket.Event>()

    init {
        webSocketCloud.connectionEvents()
            .subscribeOn(schedulersFactory.io())
            .subscribe(webSocketEvents::onNext, Timber::e)

        localIssuer.issuersLive() // emits each time issuers cache has been updated
            .subscribeOn(schedulersFactory.io())
            .filter(::isDefaultLocalIssuersUpToDate) // ignore stale data
            .zipWith(webSocketEvents) { _, event -> event }
            .filter { it is WebSocket.Event.OnConnectionOpened<*> } // wait for socket open
            .flatMap {
                localIssuer.issuers()
                    .map(issuerLocalConverter::convertList)
                    .flatMapObservable { Observable.fromIterable(it) }
                    .map { issuer ->
                        WsSubscribeEntity(
                            type = WsSubscribeType.SUBSCRIBE,
                            ticker = issuer.ticker
                        )
                    }
            }
            .subscribe(webSocketCloud::subscribe, Timber::e) // send requests to socket
    }

    override fun realTimeQuotes(): Flowable<List<Quote>> =
        webSocketCloud.quotes().map { wsQuoteNetworkConverter.convertList(it.data) }

    private inline fun <reified T> isDefaultLocalIssuersUpToDate(data: List<T>): Boolean =
        StockListRepositoryImpl.isQuotesCacheUpToDate(data, sharedPrefs)
}
