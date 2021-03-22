package com.orcchg.yandexcontest.stocklist

import com.orcchg.yandexcontest.coremodel.money
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.domain.usecase.FindIssuersByQueryUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetDefaultIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetFavouriteIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.SetIssuerFavouriteUseCase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StockListInteractorImpl @Inject constructor(
    private val findIssuersByQueryUseCase: FindIssuersByQueryUseCase,
    private val getDefaultIssuersUseCase: GetDefaultIssuersUseCase,
    private val getFavouriteIssuersUseCase: GetFavouriteIssuersUseCase,
    private val setIssuerFavouriteUseCase: SetIssuerFavouriteUseCase
) : StockListInteractor {

    override fun issuers(): Single<List<Issuer>> = getDefaultIssuersUseCase.source()

    override fun favouriteIssuers(): Single<List<Issuer>> = getFavouriteIssuersUseCase.source()

    override fun setIssuerFavourite(ticker: String, isFavourite: Boolean): Completable =
        setIssuerFavouriteUseCase.source {
            SetIssuerFavouriteUseCase.PARAM_TICKER of ticker
            SetIssuerFavouriteUseCase.PARAM_IS_FAVOURITE of isFavourite
        }

    override fun quote(ticker: String): Single<Quote> = Single.just(Quote(1725.0.money(), 2.56.money()))

    override fun stocks(): Single<List<Stock>> = Single.just(emptyList())

    override fun favouriteStocks(): Single<List<Stock>> = Single.just(emptyList())

    override fun findStocks(querySource: Observable<String>): Observable<List<Stock>> =
        querySource.switchMap { query ->
            findIssuersByQueryUseCase.source { FindIssuersByQueryUseCase.PARAM_QUERY of query }
                .map { emptyList<Stock>() } // TODO: zip with quotes
                .toObservable()
        }
}
