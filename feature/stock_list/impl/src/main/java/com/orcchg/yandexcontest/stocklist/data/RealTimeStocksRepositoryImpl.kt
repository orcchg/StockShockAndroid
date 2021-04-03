package com.orcchg.yandexcontest.stocklist.data

import android.annotation.SuppressLint
import com.orcchg.yandexcontest.core.featureflags.api.FeatureFlagManager
import com.orcchg.yandexcontest.core.network.api.WsSubscribeType
import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.times
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.QuoteDao
import com.orcchg.yandexcontest.stocklist.data.local.StockListSharedPrefs
import com.orcchg.yandexcontest.stocklist.data.local.convert.QuoteDboConverter
import com.orcchg.yandexcontest.stocklist.data.remote.StockListWebSocket
import com.orcchg.yandexcontest.stocklist.data.remote.convert.WsQuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.model.WsSubscribeEntity
import com.orcchg.yandexcontest.stocklist.data.api.RealTimeStocksRepository
import com.tinder.scarlet.WebSocket
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@SuppressLint("CheckResult")
class RealTimeStocksRepositoryImpl @Inject constructor(
    private val localIssuer: IssuerDao,
    private val localQuotes: QuoteDao,
    private val quoteLocalConverter: QuoteDboConverter,
    private val webSocketCloud: StockListWebSocket,
    private val wsQuoteNetworkConverter: WsQuoteNetworkConverter,
    private val sharedPrefs: StockListSharedPrefs,
    featureFlagManager: FeatureFlagManager,
    schedulersFactory: SchedulersFactory
) : RealTimeStocksRepository {

    private val issuersWorkSet = mutableSetOf<String>()
    private val issuersTotal = AtomicInteger(0)
    private val invalidations = PublishSubject.create<Boolean>()
    private val webSocketEvents = BehaviorSubject.create<WebSocket.Event>()

    init {
        if (featureFlagManager.isRealTimeQuotesEnabled()) {
            Timber.v("Real-Time quote updates are enabled")

            webSocketCloud.connectionEvents()
                .subscribeOn(schedulersFactory.io())
                .doOnNext { Timber.v("Socket event: $it") }
                .subscribe(webSocketEvents::onNext, Timber::e)

            localIssuer.issuersLive() // emits each time issuers local cache has been updated
                .subscribeOn(schedulersFactory.io())
                .publish { local ->
                    // if issuers local cache hasn't changed, manual invalidation will trigger socket subscriptions
                    Observable.merge(
                        local.doOnNext { Timber.v("ISSUERS TABLE CHANGE") },
                        invalidations.hide().flatMapSingle { localIssuer.issuers() }.takeUntil(local)
                    )
                }
                .filter { issuers ->
                    // check whether issuers' total count has changed that means
                    // it was not a change of properties but new entries have been added
                    val size = issuersTotal.getAndSet(issuers.size)
                    issuers.isNotEmpty() && size < issuers.size
                }
                .zipWith(webSocketEvents) { issuers, event -> issuers to event }
                // wait for socket to open
                .filter { (_, event) -> event is WebSocket.Event.OnConnectionOpened<*> }
                .flatMap { (issuers, _) ->
                    // consider only case when there are new issuers have appeared in local cache
                    // ignore any database updates related to changes in entries' properties
                    val tickers = issuers.map { it.ticker }
                    val delta = tickers.minus(issuersWorkSet)
                    val modified = issuersWorkSet.addAll(delta)
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
    }

    override fun realTimeQuotes(): Flowable<List<Quote>> =
        webSocketCloud.quotes()
            .map { quotes ->
                val data = quotes.data
                    .filter { !Money.isZero(it.price) }
                    .distinctBy { it.ticker }

                wsQuoteNetworkConverter.convertList(data)
            }
            .flatMap { quotes ->
                Flowable.fromCallable {
                    localQuotes.addQuotes(quoteLocalConverter.revertList(quotes))
                    quotes
                }
            }

    override fun invalidate(): Completable =
        Completable.fromAction {
            Timber.v("Real-time repository invalidation requested")
            issuersTotal.set(0) // pass over filter when manual invalidation has been requested
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

    @Suppress("Unused")
    private fun fakeRealTimeQuotes(): Flowable<List<Quote>> =
        localIssuer.issuers()
            .flatMap {
                Observable.fromIterable(it).take(10)
                    .flatMapMaybe { issuer ->
                        localQuotes.quote(issuer.ticker).map(quoteLocalConverter::convert)
                    }
                    .toList()
            }
            .flatMapPublisher { quotes ->
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                    .flatMapSingle { value ->
                        Flowable.fromIterable(quotes)
                            .map {
                                val percent = it.currentPrice * (value * 0.01)
                                it.copy(currentPrice = it.currentPrice + percent)
                            }
                            .toList()
                    }
            }
}
