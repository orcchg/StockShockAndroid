package com.orcchg.yandexcontest.stocklist.data

import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.QuoteDao
import com.orcchg.yandexcontest.stocklist.data.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.local.convert.QuoteDboConverter
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.local.model.QuoteDbo
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

@Suppress("CheckResult")
class StockListRepositoryImpl @Inject constructor(
    private val localIssuer: IssuerDao,
    private val localQuotes: QuoteDao,
    private val issuerLocalConverter: IssuerDboConverter,
    private val quoteLocalConverter: QuoteDboConverter,
    schedulersFactory: SchedulersFactory
) : StockListRepository {

    private val _favouriteIssuersChanged = PublishSubject.create<IssuerFavourite>()
    override val favouriteIssuersChanged: Observable<IssuerFavourite> = _favouriteIssuersChanged
    override val missingQuotes: Observable<Collection<Quote>> = Observable.empty()

    init {
        // fill local cache with fakes
        Completable.fromAction {
            val issuers = listOf(
                IssuerDbo(ticker = "AAPL", isFavourite = true),
                IssuerDbo(ticker = "MSFT", isFavourite = true),
                IssuerDbo(ticker = "FSLY"),
                IssuerDbo(ticker = "AZN"),
                IssuerDbo(ticker = "FB"),
                IssuerDbo(ticker = "GOOGL", isFavourite = true),
                IssuerDbo(ticker = "AAL", isFavourite = true),
                IssuerDbo(ticker = "NET", isFavourite = true),
                IssuerDbo(ticker = "PFE"),
                IssuerDbo(ticker = "MRNA", isFavourite = true)
            )

            val quotes = listOf(
                QuoteDbo(ticker = "AAPL", currentPrice = "$122.23", prevClosePrice = "$120.01"),
                QuoteDbo(ticker = "MSFT", currentPrice = "$230.5", prevClosePrice = "$234.72"),
                QuoteDbo(ticker = "FSLY", currentPrice = "$234.05", prevClosePrice = "$231.21"),
                QuoteDbo(ticker = "AZN", currentPrice = "$81.43", prevClosePrice = "$95.14"),
                QuoteDbo(ticker = "FB", currentPrice = "$281.99", prevClosePrice = "$283.11"),
                QuoteDbo(ticker = "GOOGL", currentPrice = "$1890", prevClosePrice = "$1924.36"),
                QuoteDbo(ticker = "AAL", currentPrice = "$21.24", prevClosePrice = "$18.9"),
                QuoteDbo(ticker = "NET", currentPrice = "$560", prevClosePrice = "$542.5"),
                QuoteDbo(ticker = "PFE", currentPrice = "$108.4", prevClosePrice = "$111.13"),
                QuoteDbo(ticker = "MRNA", currentPrice = "$178.4", prevClosePrice = "$133.21")
            )

            localIssuer.addIssuers(issuers)
            localQuotes.addQuotes(quotes)
        }
            .subscribeOn(schedulersFactory.io())
            .subscribe({}, Timber::e)
    }

    // network is stub, only local data is available
    override fun issuer(ticker: String): Maybe<Issuer> = localIssuer(ticker)

    // network is stub, only local data is available
    override fun defaultIssuers(): Single<List<Issuer>> = localIssuers()

    // network is stub, only local data is available
    override fun favouriteIssuers(): Single<List<Issuer>> = localFavouriteIssuers()

    override fun localIssuer(ticker: String): Maybe<Issuer> =
        localIssuer.issuer(ticker).map(issuerLocalConverter::convert)

    override fun localIssuers(): Single<List<Issuer>> =
        localIssuer.issuers().map(issuerLocalConverter::convertList)

    override fun localFavouriteIssuers(): Single<List<Issuer>> =
        localIssuer.favouriteIssuers().map(issuerLocalConverter::convertList)

    override fun findIssuers(query: String): Single<List<Issuer>> =
        localIssuer.findIssuers("$query%").map(issuerLocalConverter::convertList)

    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        Completable.fromAction {
            val partial = IssuerFavourite(ticker, isFavourite)
            localIssuer.setIssuerFavourite(partial)
            _favouriteIssuersChanged.onNext(partial)
        }

    override fun quote(ticker: String): Single<Quote> =
        localQuotes.quote(ticker)
            .map(quoteLocalConverter::convert)
            .toSingle(Quote(ticker)) // empty quote

    override fun emptyQuote(ticker: String): Single<Quote> = Single.just(Quote(ticker))

    // operation not supported
    override fun getMissingQuotes(): Completable = Completable.complete()

    // operation not supported
    override fun invalidateCache(): Completable = Completable.complete()
}
