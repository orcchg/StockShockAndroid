package com.orcchg.yandexcontest.stocklist.data

import android.text.format.DateUtils.DAY_IN_MILLIS
import com.orcchg.yandexcontest.stocklist.api.model.Index
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.StockListSharedPrefs
import com.orcchg.yandexcontest.stocklist.data.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerFavouriteDbo
import com.orcchg.yandexcontest.stocklist.data.remote.StockListRest
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IndexNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.IssuerNetworkConverter
import com.orcchg.yandexcontest.stocklist.data.remote.convert.QuoteNetworkConverter
import com.orcchg.yandexcontest.stocklist.domain.StockListRepository
import com.squareup.moshi.JsonDataException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StockListRepositoryImpl @Inject constructor(
    private val cloud: StockListRest,
    private val localIssuer: IssuerDao,
    private val indexNetworkConverter: IndexNetworkConverter,
    private val issuerDboConverter: IssuerDboConverter,
    private val issuerNetworkConverter: IssuerNetworkConverter,
    private val quoteNetworkConverter: QuoteNetworkConverter,
    private val sharedPrefs: StockListSharedPrefs
) : StockListRepository {

    override fun defaultIssuers(): Single<List<Issuer>> =
        defaultNetworkIssuers().toObservable()
            .publish { network -> Observable.merge(network, defaultLocalIssuers().takeUntil(network)) }
            .first(emptyList())

    override fun favouriteIssuers(): Single<List<Issuer>> =
        localIssuer.favouriteIssuers().map(issuerDboConverter::convertList)

    override fun findIssuers(query: String): Single<List<Issuer>> =
        localIssuer.findIssuers("$query%").map(issuerDboConverter::convertList)

    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        Completable.fromCallable {
            val partial = IssuerFavouriteDbo(ticker, isFavourite)
            localIssuer.setIssuerFavourite(partial)
        }

    override fun quote(ticker: String): Single<Quote> =
        cloud.quote(ticker).map(quoteNetworkConverter::convert)

    private fun defaultLocalIssuers(): Observable<List<Issuer>> =
        localIssuer.issuers()
            .filter(::isDefaultLocalIssuersUpToDate)
            .map(issuerDboConverter::convertList)
            .toObservable()

    private fun defaultNetworkIssuers(): Single<List<Issuer>> =
        popularIndex()
            .flatMapObservable { index ->
                // limit by 30 requests per second to avoid HTTP 429 from Finnhub
                val chunks = index.tickers.chunked(29)
                Observable.fromIterable(chunks)
                    .zipWith(Observable.range(0, chunks.size)) { c, i -> c to i }
                    .flatMap { (c, i) -> Observable.just(c).delay(if (i > 0) 1000L else 0L, TimeUnit.MILLISECONDS) }
                    .concatMap { chunk ->
                        Timber.v("Issuers: ${chunk.joinToString(", ")}")
                        Observable.fromIterable(chunk)
                            .flatMapSingle(cloud::issuer)
                            .onErrorResumeNext(Function { error ->
                                if (error is JsonDataException) {
                                    Observable.empty()
                                } else {
                                    Observable.error(error)
                                }
                            })
                            .map(issuerNetworkConverter::convert)
                    }
            }
            .toList()
            .flatMap { issuers ->
                Completable.fromCallable { localIssuer.addIssuers(issuerDboConverter.revertList(issuers)) }
                    .doOnComplete { sharedPrefs.recordDefaultIssuersCacheTimestamp(System.currentTimeMillis()) }
                    .toSingleDefault(issuers)
            }

    private inline fun <reified T> isDefaultLocalIssuersUpToDate(data: List<T>): Boolean =
        data.isNotEmpty() &&
            (System.currentTimeMillis() - sharedPrefs.getDefaultIssuersCacheTimestamp()) < DAY_IN_MILLIS

    private fun index() = cloud.index(symbol = "^GSPC").map(indexNetworkConverter::convert)

    private fun popularIndex() =
        Single.just(Index(
            name = "POPULAR",
            tickers = listOf(
                // first 29
                "AAPL", "MRNA", "NFLX", "GOOGL", "TSLA", "B", "MSFT", "AMZN", "WU", "BBY", "ZM",
                "PFE", "NKLA", "ATVI", "PTON", "GM", "GE", "UBER", "DAL", "BYND", "AAL", "AA",
                "T", "WDC"," QCOM", "PLTR", "DE", "FB", "BLK",
                // next
                "BIDU", "BABA", "BA", "CAT", "PYPL"
                // next 29
//                "WU", "BBY", "ZM", "PFE", "PTON", "NKLA", "REGI", "ATVI", "BYND", "GM", "GE",
//                "UBER", "DAL", "AAL", "T", "WDC", "QCOM", "PLTR", "BLK", "DE", "FB", "BIDU", "BABA",
//                "BA", "CAT", "ESS", "WMT", "TIF", "IBM"
            )
        ))
}
