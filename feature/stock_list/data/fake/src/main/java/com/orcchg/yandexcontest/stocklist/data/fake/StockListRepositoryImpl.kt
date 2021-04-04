package com.orcchg.yandexcontest.stocklist.data.fake

import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.data.api.StockListRepository
import com.orcchg.yandexcontest.stocklist.data.fake.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.fake.local.QuoteDao
import com.orcchg.yandexcontest.stocklist.data.fake.local.convert.IssuerDboConverter
import com.orcchg.yandexcontest.stocklist.data.fake.local.convert.QuoteDboConverter
import com.orcchg.yandexcontest.stocklist.data.fake.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.fake.local.model.QuoteDbo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
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

    override val favouriteIssuersChanged: Observable<IssuerFavourite> = Observable.empty()
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
    
    override fun defaultIssuers(): Single<List<Issuer>> = localIssuers()

    override fun favouriteIssuers(): Single<List<Issuer>> = localFavouriteIssuers()

    override fun localIssuers(): Single<List<Issuer>> =
        localIssuer.issuers().map(issuerLocalConverter::convertList)

    override fun localFavouriteIssuers(): Single<List<Issuer>> =
        localIssuer.favouriteIssuers().map(issuerLocalConverter::convertList)

    // operation not supported
    override fun findIssuers(query: String): Single<List<Issuer>> =
        Single.just(emptyList())

    // operation not supported
    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        Completable.complete()

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
