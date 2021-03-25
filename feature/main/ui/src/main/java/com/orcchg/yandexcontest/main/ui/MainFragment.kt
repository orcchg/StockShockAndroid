package com.orcchg.yandexcontest.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.di.DaggerMainFragmentComponent
import com.orcchg.yandexcontest.main.ui.databinding.MainFragmentBinding
import com.orcchg.yandexcontest.main.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.main.viewmodel.SearchFlowViewModelFactory
import com.orcchg.yandexcontest.search_bar.ui.SearchBarView
import javax.inject.Inject

internal class MainFragment : BaseFragment(R.layout.main_fragment) {

    @Inject lateinit var factory: SearchFlowViewModelFactory
    private val binding by viewBindings(MainFragmentBinding::bind)
    private val viewModel by activityViewModels<SearchFlowViewModel> { factory }

    override fun onAttach(context: Context) {
        DaggerMainFragmentComponent.create().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.searchBar) {
            onBackPressedListener = SearchBarView.OnBackPressedListener {
                binding.rootContainer.requestFocus()
                closeSearchResultsScreenIfNeed()
                closeSearchSuggestScreenIfNeed()
            }
            onFocusGainListener = SearchBarView.OnFocusGainListener {
                openSearchSuggestScreenIfNeed()
            }
            onTextChangedListener = SearchBarView.OnTextChangedListener {
                if (it.isNullOrBlank()) {
                    closeSearchResultsScreenIfNeed()
                } else {
                    viewModel.sendSearchRequest(it.toString())
                    openSearchResultsScreenIfNeed(initialQuery = it.toString())
                }
            }
        }
        observe(viewModel.prepareRequestInput, binding.searchBar::setText)
    }

    private fun closeSearchResultsScreenIfNeed() {
        requireActivity().findNavController(R.id.nav_subhost_fragment)
            .takeIf { it.currentDestination?.id == R.id.main_search_result_fragment }
            ?.navigateUp()
    }

    private fun closeSearchSuggestScreenIfNeed() {
        requireActivity().findNavController(R.id.nav_subhost_fragment)
            .takeIf { it.currentDestination?.id == R.id.main_search_suggest_fragment }
            ?.navigateUp()
    }

    private fun openSearchResultsScreenIfNeed(initialQuery: String) {
        requireActivity().findNavController(R.id.nav_subhost_fragment)
            .takeIf { it.currentDestination?.id != R.id.main_search_result_fragment }
            ?.navigate(MainNavSubgraphDirections.navActionOpenSearchResultFragment(initialQuery))
    }

    private fun openSearchSuggestScreenIfNeed() {
        requireActivity().findNavController(R.id.nav_subhost_fragment)
            .takeIf { it.currentDestination?.id != R.id.main_search_suggest_fragment }
            ?.navigate(MainNavSubgraphDirections.navActionOpenSearchSuggestFragment())
    }
}
