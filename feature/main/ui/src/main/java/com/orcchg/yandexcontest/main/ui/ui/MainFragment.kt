package com.orcchg.yandexcontest.main.ui.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.ui.R
import com.orcchg.yandexcontest.main.ui.databinding.MainFragmentBinding
import com.orcchg.yandexcontest.main.ui.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.main.ui.viewmodel.SearchFlowViewModelFactory
import com.orcchg.yandexcontest.search_bar.ui.SearchBarView
import javax.inject.Inject

internal class MainFragment : BaseFragment(R.layout.main_fragment) {

    @Inject lateinit var factory: SearchFlowViewModelFactory
    private val binding by viewBindings(MainFragmentBinding::bind)
    private val viewModel by viewModels<SearchFlowViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: inject
        super.onCreate(savedInstanceState)
        with(binding.searchBar) {
            onBackPressedListener = SearchBarView.OnBackPressedListener {
                // TODO: closeSearchResultsScreenIfNeed()
            }
            onTextChangedListener = SearchBarView.OnTextChangedListener {
                viewModel.sendSearchRequest(it.toString())
                // TODO: openSearchResultsScreenIfNeed(initialQuery = it.toString())
            }
        }
    }
}
