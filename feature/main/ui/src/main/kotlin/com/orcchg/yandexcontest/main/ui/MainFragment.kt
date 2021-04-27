package com.orcchg.yandexcontest.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
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

@Suppress("AutoDispose", "CheckResult")
internal class MainFragment : BaseFragment(R.layout.main_fragment) {

    @Inject lateinit var factory: SearchFlowViewModelFactory
    private val binding by viewBindings(MainFragmentBinding::bind)
    private val viewModel by activityViewModels<SearchFlowViewModel> { factory }

    private val navListener by lazy(LazyThreadSafetyMode.NONE) {
        NavController.OnDestinationChangedListener { _, destination, _ ->
            binding.appBarLayout.post {
                when (destination.id) {
                    R.id.main_search_result_fragment -> {
                        // this screen should bring search bar in the focused state
                        binding.searchBar.requestFocus()
                        binding.appBarLayout.setExpanded(true, false)
                    }
                    R.id.main_stock_details_fragment -> {
                        // search bar is hidden due to another screen is opened
                        binding.searchBar.clearFocus() // and hide soft keyboard as well
                        binding.appBarLayout.setExpanded(false, false)
                    }
                }
            }
        }
    }

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
                /**
                 * Focusing [SearchBarView] brings search suggest screen open,
                 * if the latter or search result screen hasn't already opened.
                 */
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

    @Suppress("Deprecation")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController().addOnDestinationChangedListener(navListener)
    }

    private fun closeSearchResultsScreenIfNeed() {
        navController()
            .takeIf { it.currentDestination?.id == R.id.main_search_result_fragment }
            ?.navigateUp()
    }

    private fun closeSearchSuggestScreenIfNeed() {
        navController()
            .takeIf { it.currentDestination?.id == R.id.main_search_suggest_fragment }
            ?.navigateUp()
    }

    private fun openSearchResultsScreenIfNeed(initialQuery: String) {
        navController()
            .takeIf {
                val id = it.currentDestination?.id
                // open search result screen if not opened yet
                id != R.id.main_search_result_fragment
            }
            ?.navigate(MainNavSubgraphDirections.navActionOpenSearchResultFragment(initialQuery))
    }

    private fun openSearchSuggestScreenIfNeed() {
        navController()
            .takeIf {
                val id = it.currentDestination?.id
                // open search suggest screen if not opened yet
                id != R.id.main_search_suggest_fragment &&
                    // don't open search suggest screen if search result screen is opened
                    id != R.id.main_search_result_fragment
            }
            ?.navigate(MainNavSubgraphDirections.navActionOpenSearchSuggestFragment())
    }

    private fun navController() = requireActivity().findNavController(R.id.main_nav_subhost_fragment)
}
