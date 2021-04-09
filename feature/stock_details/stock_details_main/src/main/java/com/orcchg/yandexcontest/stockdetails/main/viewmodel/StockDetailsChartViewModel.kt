package com.orcchg.yandexcontest.stockdetails.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stockdetails.api.StockDetailsInteractor
import com.orcchg.yandexcontest.stockdetails.api.Ticker
import com.orcchg.yandexcontest.stockdetails.api.model.Candle
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import timber.log.Timber
import javax.inject.Inject

internal class StockDetailsChartViewModel @Inject constructor(
    @Ticker private val ticker: String,
    private val interactor: StockDetailsInteractor
) : AutoDisposeViewModel() {

    private val _candles by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<Candle>>>()
        // TODO: save selected resolution and ts to SavedState
        candlesInternal(data, Candle.Resolution.Day, 0L, System.currentTimeMillis())
        data
    }
    internal val candles: LiveData<DataState<List<Candle>>> = _candles

    fun candles(
        resolution: Candle.Resolution,
        fromTs: Long,
        toTs: Long
    ) {
        candlesInternal(_candles, resolution, fromTs, toTs)
    }

    private fun candlesInternal(
        data: MutableLiveData<DataState<List<Candle>>>,
        resolution: Candle.Resolution,
        fromTs: Long,
        toTs: Long
    ) {
        interactor.candles(
            ticker = ticker,
            resolution = resolution,
            fromTs = fromTs,
            toTs = toTs
        )
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
    }
}
