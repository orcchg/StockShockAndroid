package com.orcchg.yandexcontest.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.search.api.SearchInteractor
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import timber.log.Timber
import javax.inject.Inject

internal class SearchSuggestViewModel @Inject constructor(
    private val interactor: SearchInteractor
) : AutoDisposeViewModel() {

    private val _popularSearch by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<String>>>()
        interactor.popularSearch()
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
    internal val popularSearch: LiveData<DataState<List<String>>> = _popularSearch

    private val _recentSearch by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<String>>>()
        interactor.recentSearch()
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
    internal val recentSearch: LiveData<DataState<List<String>>> = _recentSearch

    fun addRecentSearch(item: String) {
        if (item.isBlank()) {
            return // ignore empty search items
        }

        interactor.addRecentSearch(item)
            .autoDispose(this)
            .subscribe({}, Timber::e)
    }
}
