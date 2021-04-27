package com.orcchg.yandexcontest.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import javax.inject.Inject

internal class SearchFlowViewModel @Inject constructor() : AutoDisposeViewModel() {

    private val _prepareRequestInput = MutableLiveData<String>()
    private val _searchRequest = MutableLiveData<String>()
    internal val prepareRequestInput: LiveData<String> = _prepareRequestInput
    internal val searchRequest: LiveData<String> = _searchRequest

    fun prepareSearchRequestInput(request: String) {
        _prepareRequestInput.value = request
    }

    fun sendSearchRequest(request: String) {
        _searchRequest.value = request
    }
}
