package com.orcchg.yandexcontest.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import javax.inject.Inject

internal class StockPagesViewModel @Inject constructor() : AutoDisposeViewModel() {

    private val _stockSelection = MutableLiveData<StockSelection>()
    internal val stockSelection: LiveData<StockSelection> = _stockSelection

    fun onPageSelected(stockSelection: StockSelection) {
        _stockSelection.value = stockSelection
    }
}
