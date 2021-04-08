package com.orcchg.yandexcontest.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.argument
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
    private val initialQuery by argument<String>("initialQuery")
    private val binding by viewBindings(MainSearchResultFragmentBinding::bind)
    private val viewModel by viewModels<StockResultViewModel> { factory }
    private val sharedViewModel by activityViewModels<SearchFlowViewModel>()

    override fun onAttach(context: Context) {
        DaggerSearchResultFragmentComponent.factory()
            .create(
                initialQuery = initialQuery.orEmpty(),
                searchFeatureApi = api.getFeature(),
                stockListFeatureApi = api.getFeature()
            )
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockListAdapter.itemClickListener = {
            // TODO: stock item click
        }
        stockListAdapter.favIconClickListener = {
            viewModel.setIssuerFavourite(it.ticker, it.isFavourite)
        }
        binding.rvItems.adapter = stockListAdapter

        observe(viewModel.stocks) {
            it.onLoading { showLoading(true) }
            it.onSuccess { data ->
                stockListAdapter.update(data)
                showError(false)
                showLoading(false)
            }
            it.onFailure { showError(true) }
        }
        observe(sharedViewModel.searchRequest, viewModel::addRecentSearch)
        observe(sharedViewModel.searchRequest, viewModel::findStocks)
    }

    private fun showLoading(isShow: Boolean) {
        binding.pbLoading.isInvisible = !isShow
        binding.tvError.isVisible = false
        binding.btnError.isVisible = false
    }

    private fun showError(isShow: Boolean) {
        binding.pbLoading.isInvisible = isShow
        binding.tvError.isVisible = isShow
        binding.btnError.isVisible = isShow
    }
}
