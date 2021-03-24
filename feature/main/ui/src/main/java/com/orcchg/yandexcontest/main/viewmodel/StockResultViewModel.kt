package com.orcchg.yandexcontest.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.convert.StockVoConverter
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

internal class StockResultViewModel @Inject constructor(
    @Named("initialQuery") private val initialQuery: String,
    private val interactor: StockListInteractor,
    private val stockVoConverter: StockVoConverter
) : AutoDisposeViewModel() {

    private val querySubject = BehaviorSubject.createDefault(initialQuery)

    private val _stocks by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<StockVO>>>()
        interactor.findStocks(querySubject.hide())
            .subscribeOn(Schedulers.computation())
            .doOnSubscribe { data.value = DataState.loading() }
            .map(stockVoConverter::convertList)
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(this)
            .subscribe({
                data.value = DataState.success(it)
            }, {
                Timber.e(it)
                data.value = DataState.failure(it)
            })
        data
    }
    internal val stocks: LiveData<DataState<List<StockVO>>> by lazy(LazyThreadSafetyMode.NONE) { _stocks }

    fun findStocks(query: String) {
        querySubject.onNext(query)
    }

    fun setIssuerFavourite(ticker: String, isFavourite: Boolean) {
        interactor.setIssuerFavourite(ticker, isFavourite)
            .autoDispose(this)
            .subscribe({}, Timber::e)
    }
}
