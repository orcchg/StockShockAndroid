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
import com.orcchg.yandexcontest.main.di.DaggerSearchResultFragmentComponent
import com.orcchg.yandexcontest.main.ui.databinding.MainSearchResultFragmentBinding
import com.orcchg.yandexcontest.main.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.main.viewmodel.StockResultViewModel
import com.orcchg.yandexcontest.main.viewmodel.StockResultViewModelFactory
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import com.orcchg.yandexcontest.util.onFailure
import com.orcchg.yandexcontest.util.onLoading
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class SearchResultFragment : BaseFragment(R.layout.main_search_result_fragment) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockResultViewModelFactory
    private val binding by viewBindings(MainSearchResultFragmentBinding::bind)
    private val viewModel by viewModels<StockResultViewModel> { factory }
    private val sharedViewModel by activityViewModels<SearchFlowViewModel>()

    override fun onAttach(context: Context) {
        DaggerSearchResultFragmentComponent.factory()
            .create(
                initialQuery = arguments?.getString("initialQuery").orEmpty(),
                featureApi = api.getFeature()
            )
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockListAdapter.itemClickListener = {
            // TODO: click list
        }
        binding.stockList.rvItems.adapter = stockListAdapter
        observe(viewModel.stocks) {
            // TODO: load / error
            it.onLoading { }
            it.onSuccess(stockListAdapter::update)
            it.onFailure { }
        }
        observe(sharedViewModel.searchRequest, viewModel::findStocks)
    }
}
