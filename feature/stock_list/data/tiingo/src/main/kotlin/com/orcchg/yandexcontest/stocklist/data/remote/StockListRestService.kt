package com.orcchg.yandexcontest.stocklist.data.remote

import com.orcchg.yandexcontest.stocklist.data.BaseStockListRepositoryImpl
import com.orcchg.yandexcontest.stocklist.data.remote.model.IndexEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.IssuerEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import com.orcchg.yandexcontest.util.`toYYYY-MM-DD`
import com.orcchg.yandexcontest.util.yesterday
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class StockListRestService @Inject constructor(
    private val rest: StockListRest
) :
    IStockListRestForIndex<IndexEntity>,
    IStockListRestForIssuer<IssuerEntity>,
    IStockListRestForQuote<QuoteEntity> {

    override fun index(symbol: String): Single<IndexEntity> =
        Single.fromCallable {
            IndexEntity(
                tickers = BaseStockListRepositoryImpl.popularIndexContent(),
                name = BaseStockListRepositoryImpl.POPULAR_INDEX
            )
        }

    override fun issuer(ticker: String): Single<IssuerEntity> =
        rest.issuer(ticker)

    override fun quote(ticker: String): Maybe<QuoteEntity> =
        rest.quote(
            ticker = ticker,
            startDate = yesterday().`toYYYY-MM-DD`()
        )
            .filter {
                /**
                 * Tiingo implementation implies that if [StockListRest.quote] responds
                 * with empty list of [QuoteEntity], then treat this as a failure and a
                 * subject for retry later.
                 */
                it.isNotEmpty()
            }
            .map { list ->
                val lastTwo = list.takeLast(2)
                val previous = lastTwo.first()
                val recent = lastTwo.last()
                recent.copy(prevClosePrice = previous.currentPrice)
            }
}
