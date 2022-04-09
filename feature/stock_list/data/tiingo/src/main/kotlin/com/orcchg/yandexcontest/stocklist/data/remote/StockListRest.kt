package com.orcchg.yandexcontest.stocklist.data.remote

import com.orcchg.yandexcontest.stocklist.data.BaseStockListRepositoryImpl
import com.orcchg.yandexcontest.stocklist.data.BaseStockListRepositoryImpl.Companion.POPULAR_INDEX
import com.orcchg.yandexcontest.stocklist.data.remote.model.IndexEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.IssuerEntity
import com.orcchg.yandexcontest.stocklist.data.remote.model.QuoteEntity
import com.orcchg.yandexcontest.util.`toYYYY-MM-DD`
import com.orcchg.yandexcontest.util.yesterday
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockListRest :
    IStockListRestForIndex<IndexEntity>,
    IStockListRestForIssuer<IssuerEntity>,
    IStockListRestForQuote<QuoteEntity> {

    override fun index(symbol: String): Single<IndexEntity> =
        Single.fromCallable {
            IndexEntity(
                tickers = BaseStockListRepositoryImpl.popularIndexContent(),
                name = POPULAR_INDEX
            )
        }

    @GET("daily/{ticker}")
    override fun issuer(@Path("ticker") ticker: String): Single<IssuerEntity>

    @GET("daily/{ticker}/prices")
    fun quote(
        @Path("ticker") ticker: String,
        @Query("startDate", encoded = true) startDate: String
    ): Single<List<QuoteEntity>>

    override fun quote(ticker: String): Single<QuoteEntity> =
        quote(
            ticker = ticker,
            startDate = yesterday().`toYYYY-MM-DD`()
        )
            .map { list ->
                val lastTwo = list.takeLast(2)
                val previous = lastTwo.first()
                val recent = lastTwo.last()
                recent.copy(prevClosePrice = previous.currentPrice)
            }
}
