package com.orcchg.yandexcontest.search.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import javax.inject.Inject

internal class SearchFlowViewModel @Inject constructor() : AutoDisposeViewModel() {

    private val _searchRequest = MutableLiveData("")
    internal val searchRequest: LiveData<String> = _searchRequest

    internal fun init() { /* called to init this ViewModel prior it to be shared */ }

    fun sendSearchRequest(request: String) {
        _searchRequest.value = request
    }
}
