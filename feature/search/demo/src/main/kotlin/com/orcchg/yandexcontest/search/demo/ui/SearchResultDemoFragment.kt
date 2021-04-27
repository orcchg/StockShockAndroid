package com.orcchg.yandexcontest.search.demo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.detachableAdapter
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.showToast
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.fake.di.DaggerFakeStockListFeatureComponent
import com.orcchg.yandexcontest.search.demo.R
import com.orcchg.yandexcontest.search.demo.databinding.SearchResultDemoFragmentBinding
import com.orcchg.yandexcontest.search.demo.di.DaggerSearchResultDemoFragmentComponent
import com.orcchg.yandexcontest.search.demo.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.search.demo.viewmodel.StockResultViewModel
import com.orcchg.yandexcontest.search.demo.viewmodel.StockResultViewModelFactory
import com.orcchg.yandexcontest.stocklist.ui.adapter.StockListAdapter
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class SearchResultDemoFragment : Fragment(R.layout.search_result_demo_fragment) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockResultViewModelFactory
    private val binding by viewBindings(SearchResultDemoFragmentBinding::bind)
    private val viewModel by viewModels<StockResultViewModel> { factory }
    private val sharedViewModel by activityViewModels<SearchFlowViewModel>()

    override fun onAttach(context: Context) {
        DaggerSearchResultDemoFragmentComponent.factory()
            .create(
                initialQuery = arguments?.getString("initialQuery").orEmpty(),
                featureApi = DaggerFakeStockListFeatureComponent.create()
            )
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockListAdapter.itemClickListener = {
            context?.showToast("Stock ${it.ticker}")
        }
        binding.rvItems.detachableAdapter = stockListAdapter
        observe(viewModel.stocks) {
            it.onSuccess(stockListAdapter::update)
        }
        observe(sharedViewModel.searchRequest, viewModel::findStocks)
    }
}
