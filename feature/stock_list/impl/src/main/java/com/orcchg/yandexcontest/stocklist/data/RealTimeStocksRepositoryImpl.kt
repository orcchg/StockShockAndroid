package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.core.network.api.WsSubscribeType
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.coremodel.Quote
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.StockListSharedPrefs
import com.orcchg.yandexcontest.stocklist.data.remote.StockListWebSocket
import com.orcchg.yandexcontest.stocklist.data.remote.convert.WsQuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.model.WsSubscribeEntity
import com.orcchg.yandexcontest.stocklist.domain.RealTimeStocksRepository
import com.tinder.scarlet.WebSocket
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

@Deprecated("quotes")
@Suppress("CheckResult")
class RealTimeStocksRepositoryImpl @Inject constructor(
    private val localIssuer: IssuerDao,
    private val webSocketCloud: StockListWebSocket,
    private val wsQuoteNetworkConverter: WsQuoteNetworkConverter,
    private val sharedPrefs: StockListSharedPrefs,
    schedulersFactory: SchedulersFactory
) : RealTimeStocksRepository {

    private val issuersWorkSet = mutableSetOf<String>()
    private val invalidations = PublishSubject.create<Boolean>()
    private val webSocketEvents = BehaviorSubject.create<WebSocket.Event>()

    init {
        webSocketCloud.connectionEvents()
            .subscribeOn(schedulersFactory.io())
            .doOnNext { Timber.v("Socket event: $it") }
            .subscribe(webSocketEvents::onNext, Timber::e)

        localIssuer.issuersLive() // emits each time issuers local cache has been updated
            .publish { local ->
                // if issuers local cache hasn't changed, manual invalidation will trigger socket subscriptions
                Observable.merge(
                    local,
                    invalidations.hide().flatMapSingle { localIssuer.issuers() }.takeUntil(local)
                )
            }
            .filter { it.isNotEmpty() }
            .subscribeOn(schedulersFactory.io())
            .zipWith(webSocketEvents) { issuers, event -> issuers to event }
            // wait for socket to open
            .filter { (_, event) -> event is WebSocket.Event.OnConnectionOpened<*> }
            .flatMap { (issuers, _) ->
                // consider only case when there are new issuers have appeared in local cache
                // ignore any database updates related to changes in entries' properties
                val tickers = issuers.map { it.ticker }
                val delta = tickers.minus(issuersWorkSet)
                val modified = issuersWorkSet.addAll(tickers)
                Timber.v("Socket is ready to receive new ${delta.size} subscription requests: $modified")
                if (modified) {
                    Observable.just(delta)
                } else { // property changes, skip
                    Observable.empty()
                }
            }
            .flatMap { tickers ->
                Observable.fromIterable(tickers)
                    .map { ticker ->
                        WsSubscribeEntity(
                            type = WsSubscribeType.SUBSCRIBE,
                            ticker = ticker
                        )
                    }
            }
            .subscribe(webSocketCloud::subscribe, Timber::e) // send requests to socket
    }

    override fun realTimeQuotes(): Flowable<List<Quote>> =
        webSocketCloud.quotes().map { wsQuoteNetworkConverter.convertList(it.data) }

    override fun invalidate(): Completable =
        Completable.fromAction {
            Timber.v("Real-time repository invalidation requested")
            invalidations.onNext(true)
        }

    @Suppress("Unused")
    private fun unsubscribe(): Completable =
        localIssuer.issuers()
            .flatMapObservable { Observable.fromIterable(it) }
            .map { issuer ->
                WsSubscribeEntity(
                    type = WsSubscribeType.UNSUBSCRIBE,
                    ticker = issuer.ticker
                )
            }
            .flatMapCompletable { unsubscribe ->
                Completable.fromCallable { webSocketCloud.subscribe(unsubscribe) }
            }
}
