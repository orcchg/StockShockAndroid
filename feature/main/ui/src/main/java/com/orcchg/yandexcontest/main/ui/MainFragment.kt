package com.orcchg.yandexcontest.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jakewharton.rxbinding3.material.offsetChanges
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.di.DaggerMainFragmentComponent
import com.orcchg.yandexcontest.main.ui.databinding.MainFragmentBinding
import com.orcchg.yandexcontest.main.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.main.viewmodel.SearchFlowViewModelFactory
import com.orcchg.yandexcontest.search_bar.ui.SearchBarView
import javax.inject.Inject
import kotlin.math.abs

@Suppress("AutoDispose", "CheckResult")
internal class MainFragment : BaseFragment(R.layout.main_fragment) {

    @Inject lateinit var factory: SearchFlowViewModelFactory
    private val binding by viewBindings(MainFragmentBinding::bind)
    private val viewModel by activityViewModels<SearchFlowViewModel> { factory }

    private var offset: Int = 0 // search bar offset
    private val navListener by lazy(LazyThreadSafetyMode.NONE) {
        NavController.OnDestinationChangedListener { _, _, args ->
            val isExpanded = offset == 0
            val isCollapsed = abs(offset) >= binding.appBarLayout.totalScrollRange
            val hideSearchBar = args?.containsKey("hide_search_bar") == true
            val status = if (hideSearchBar) false else isExpanded || !isCollapsed
            binding.appBarLayout.setExpanded(status, false)
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

        binding.appBarLayout.offsetChanges().subscribe { offset = it }
        navController().addOnDestinationChangedListener(navListener)

        observe(viewModel.prepareRequestInput, binding.searchBar::setText)
    }

    override fun onDestroyView() {
        navController().removeOnDestinationChangedListener(navListener)
        super.onDestroyView()
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
            .takeIf { it.currentDestination?.id != R.id.main_search_result_fragment }
            ?.navigate(MainNavSubgraphDirections.navActionOpenSearchResultFragment(initialQuery))
    }

    private fun openSearchSuggestScreenIfNeed() {
        navController()
            .takeIf { it.currentDestination?.id != R.id.main_search_suggest_fragment }
            ?.navigate(MainNavSubgraphDirections.navActionOpenSearchSuggestFragment())
    }

    private fun navController() = requireActivity().findNavController(R.id.main_nav_subhost_fragment)
}
