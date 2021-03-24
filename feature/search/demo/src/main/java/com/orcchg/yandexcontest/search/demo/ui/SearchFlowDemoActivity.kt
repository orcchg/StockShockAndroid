package com.orcchg.yandexcontest.search.demo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.search.demo.R
import com.orcchg.yandexcontest.search.demo.SearchFlowDemoNavGraphDirections
import com.orcchg.yandexcontest.search.demo.databinding.SearchFlowDemoActivityBinding
import com.orcchg.yandexcontest.search.demo.di.DaggerSearchFlowDemoActivityComponent
import com.orcchg.yandexcontest.search.demo.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.search.demo.viewmodel.SearchFlowViewModelFactory
import com.orcchg.yandexcontest.search_bar.ui.SearchBarView
import javax.inject.Inject

internal class SearchFlowDemoActivity : AppCompatActivity() {

    @Inject lateinit var factory: SearchFlowViewModelFactory
    private val binding by viewBindings(SearchFlowDemoActivityBinding::inflate)
    private val viewModel by viewModels<SearchFlowViewModel> { factory }

    @SuppressLint("AutoDispose", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSearchFlowDemoActivityComponent.create().inject(this)
        super.onCreate(savedInstanceState)
        with(binding.searchBar) {
            onBackPressedListener = SearchBarView.OnBackPressedListener {
                binding.rootContainer.requestFocus()
                closeSearchResultsScreenIfNeed()
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
        viewModel.init()
    }

    private fun closeSearchResultsScreenIfNeed() {
        findNavController(R.id.nav_host_fragment)
            .takeIf { it.currentDestination?.id == R.id.search_result_demo_fragment }
            ?.navigateUp()
    }

    private fun openSearchResultsScreenIfNeed(initialQuery: String) {
        findNavController(R.id.nav_host_fragment)
            .takeIf { it.currentDestination?.id != R.id.search_result_demo_fragment }
            ?.navigate(SearchFlowDemoNavGraphDirections.navActionOpenSearchResultDemoFragment(initialQuery))
    }
}
