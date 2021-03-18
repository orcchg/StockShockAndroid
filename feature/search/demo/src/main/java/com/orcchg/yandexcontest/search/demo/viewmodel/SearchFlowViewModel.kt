package com.orcchg.yandexcontest.search.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.DataState
import javax.inject.Inject

internal class SearchFlowViewModel @Inject constructor() : AutoDisposeViewModel() {

    private val _searchRequest = MutableLiveData("")
    internal val searchRequest: LiveData<String> = _searchRequest

    private val _searchResults by lazy(LazyThreadSafetyMode.NONE) {
        MutableLiveData<DataState<List<StockVO>>>()
    }
    internal val searchResults: LiveData<DataState<List<StockVO>>> by lazy(LazyThreadSafetyMode.NONE) { _searchResults }

    fun sendSearchRequest(request: String) {
        _searchRequest.value = request
    }
}
