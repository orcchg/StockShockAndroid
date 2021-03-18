package com.orcchg.yandexcontest.search.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.convert.StockVoConverter
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

internal class StockResultViewModel @Inject constructor(
    @Named("initialQuery") private val initialQuery: String,
    private val interactor: StockListInteractor,
    private val stockVoConverter: StockVoConverter
) : AutoDisposeViewModel() {

    private val _stocks by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<StockVO>>>()
        findStocks(initialQuery, data)
        data
    }
    internal val stocks: LiveData<DataState<List<StockVO>>> by lazy(LazyThreadSafetyMode.NONE) { _stocks }

    fun findStocks(query: String) {
        findStocks(query, _stocks)
    }

    private fun findStocks(query: String, data: MutableLiveData<DataState<List<StockVO>>>) {
        interactor.findStocks(query)
            .doOnSubscribe { data.value = DataState.loading() }
            .map(stockVoConverter::convertList)
            .autoDispose(this)
            .subscribe({
                data.value = DataState.success(it)
            }, {
                Timber.e(it)
                data.value = DataState.failure(it)
            })
    }
}