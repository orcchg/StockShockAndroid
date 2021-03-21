package com.orcchg.yandexcontest.main.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

internal class SearchFlowViewModelFactory @Inject constructor(
    private val provider: Provider<SearchFlowViewModel>
) : ViewModelProvider.Factory {

    @Suppress("Unchecked_Cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = provider.get() as T
}
