package com.orcchg.yandexcontest.main.ui.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.ui.R
import com.orcchg.yandexcontest.main.ui.databinding.MainSearchResultFragmentBinding
import com.orcchg.yandexcontest.main.ui.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.main.ui.viewmodel.StockResultViewModel
import com.orcchg.yandexcontest.main.ui.viewmodel.StockResultViewModelFactory
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import javax.inject.Inject

internal class SearchResultFragment : BaseFragment(R.layout.main_search_result_fragment) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockResultViewModelFactory
    private val binding by viewBindings(MainSearchResultFragmentBinding::bind)
    private val viewModel by viewModels<StockResultViewModel> { factory }
    private val sharedViewModel by activityViewModels<SearchFlowViewModel>()

    override fun onAttach(context: Context) {
        // TODO: inject
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockListAdapter.itemClickListener = {
            // TODO: click list
        }
        binding.stockList.rvItems.adapter = stockListAdapter
        // TODO: observe
    }
}
