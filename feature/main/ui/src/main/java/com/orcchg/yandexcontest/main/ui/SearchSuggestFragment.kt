package com.orcchg.yandexcontest.main.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isInvisible
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
        binding.sflPopular.onItemClickListener = SearchFlowLayout.OnItemClickListener(::handleClickOnSearchItem)
        binding.sflRecent.onItemClickListener = SearchFlowLayout.OnItemClickListener(::handleClickOnSearchItem)

        observe(viewModel.popularSearch) {
            it.onSuccess { data -> populateSearchContainer(binding.sflPopular, data) }
        }
        observe(viewModel.recentSearch) {
            it.onSuccess { data ->
                binding.tvRecentSearch.isInvisible = data.isEmpty()
                populateSearchContainer(binding.sflRecent, data)
            }
        }
        observe(sharedViewModel.searchRequest, viewModel::addRecentSearch)
    }

    @SuppressLint("AutoDispose", "CheckResult")
    private fun populateSearchContainer(container: SearchFlowLayout, items: List<String>) {
        items.map { query -> SearchLabelTextView(requireContext()).apply { text = query } }
            .forEach(container::addView)
    }

    private fun handleClickOnSearchItem(view: View) {
        if (view is TextView) {
            sharedViewModel.sendSearchRequest(view.text.toString())
        }
    }
}
