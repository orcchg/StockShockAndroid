package com.orcchg.yandexcontest.stockdetails.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stockdetails.api.Ticker
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import timber.log.Timber
import javax.inject.Inject

internal class StockDetailsMainViewModel @Inject constructor(
    @Ticker internal val ticker: String,
    private val interactor: StockListInteractor
) : AutoDisposeViewModel() {

    private val _isFavourite = MutableLiveData<Boolean>()
    internal val isFavourite: LiveData<Boolean> = _isFavourite

    private val _issuer by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<Issuer>>()
        interactor.issuer(ticker)
            .doOnSubscribe { data.value = DataState.loading() }
            .autoDispose(this)
            .subscribe(
                {
                    _isFavourite.value = it.isFavourite
                    data.value = DataState.success(it)
                },
                {
                    Timber.e(it)
                    data.value = DataState.failure(it)
                }
            )
        data
    }
    internal val issuer: LiveData<DataState<Issuer>> = _issuer

    private val _quote by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<Quote>>()
        interactor.quote(ticker)
            .doOnSubscribe { data.value = DataState.loading() }
            .autoDispose(this)
            .subscribe(
                {
                    data.value = DataState.success(it)
                },
                {
                    Timber.e(it)
                    data.value = DataState.failure(it)
                }
            )
        data
    }
    internal val quote: LiveData<DataState<Quote>> = _quote

    fun setIssuerFavourite(ticker: String) {
        _isFavourite.value?.not()?.let { newFavourite ->
            interactor.setIssuerFavourite(ticker, newFavourite)
                .autoDispose(this)
                .subscribe({ _isFavourite.value = newFavourite }, Timber::e)
        }
    }
}
