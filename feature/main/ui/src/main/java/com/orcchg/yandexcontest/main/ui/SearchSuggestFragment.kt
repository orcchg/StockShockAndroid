package com.orcchg.yandexcontest.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coredi.getFeature
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.di.DaggerSearchSuggestFragmentComponent
import com.orcchg.yandexcontest.main.ui.databinding.MainSearchSuggestFragmentBinding
import com.orcchg.yandexcontest.main.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.main.viewmodel.SearchSuggestViewModel
import com.orcchg.yandexcontest.main.viewmodel.SearchSuggestViewModelFactory
import com.orcchg.yandexcontest.search_bar.ui.SearchFlowLayout
import com.orcchg.yandexcontest.search_bar.ui.SearchLabelTextView
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class SearchSuggestFragment : BaseFragment(R.layout.main_search_suggest_fragment) {

    @Inject lateinit var factory: SearchSuggestViewModelFactory
    private val binding by viewBindings(MainSearchSuggestFragmentBinding::bind)
    private val viewModel by viewModels<SearchSuggestViewModel> { factory }
    private val sharedViewModel by activityViewModels<SearchFlowViewModel>()

    override fun onAttach(context: Context) {
        DaggerSearchSuggestFragmentComponent.factory()
            .create(featureApi = api.getFeature())
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.popularSearch) {
            it.onSuccess { data -> populateSearchContainer(binding.sflPopular, data) }
        }
        observe(viewModel.recentSearch) {
            it.onSuccess { data -> populateSearchContainer(binding.sflRecent, data) }
        }
        observe(sharedViewModel.searchRequest, viewModel::addRecentSearch)
    }

    private fun populateSearchContainer(container: SearchFlowLayout, items: List<String>) {
        items.map { SearchLabelTextView(requireContext()) }.forEach(container::addView)
    }
}
