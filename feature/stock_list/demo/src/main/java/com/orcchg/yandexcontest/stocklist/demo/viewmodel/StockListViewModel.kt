package com.orcchg.yandexcontest.stocklist.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.demo.convert.IssuerToStockVoConverter
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import timber.log.Timber
import javax.inject.Inject

internal class StockListViewModel @Inject constructor(
    private val interactor: StockListInteractor,
    private val issuerToStockVoConverter: IssuerToStockVoConverter
) : AutoDisposeViewModel() {

    private val _issuers by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<Issuer>>>()
        interactor.issuers()
            .doOnSubscribe { data.value = DataState.loading() }
            .autoDispose(this)
            .subscribe({
                data.value = DataState.success(it)
            }, {
                Timber.e(it)
                data.value = DataState.failure(it)
            })
        data
    }
    internal val issuers: LiveData<DataState<List<Issuer>>> by lazy(LazyThreadSafetyMode.NONE) { _issuers }

    private val _stocks by lazy(LazyThreadSafetyMode.NONE) {
        Transformations.map(issuers) {
            val items = it.getOrThrow().map(issuerToStockVoConverter::convert)
            DataState.success(items)
        }
    }
    internal val stocks: LiveData<DataState<List<StockVO>>> by lazy(LazyThreadSafetyMode.NONE) { _stocks }
}
