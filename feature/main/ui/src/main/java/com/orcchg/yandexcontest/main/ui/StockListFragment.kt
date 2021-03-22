package com.orcchg.yandexcontest.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coredi.getFeature
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.di.DaggerStockListFragmentComponent
import com.orcchg.yandexcontest.main.ui.databinding.MainStockListFragmentBinding
import com.orcchg.yandexcontest.main.viewmodel.StockListViewModel
import com.orcchg.yandexcontest.main.viewmodel.StockListViewModelFactory
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import com.orcchg.yandexcontest.util.onFailure
import com.orcchg.yandexcontest.util.onLoading
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class StockListFragment : BaseFragment(R.layout.main_stock_list_fragment) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockListViewModelFactory
    private val binding by viewBindings(MainStockListFragmentBinding::bind)
    private val viewModel by viewModels<StockListViewModel> { factory }

    override fun onAttach(context: Context) {
        val stockSelection = arguments?.getSerializable(BUNDLE_KEY_STOCK_SELECTION) as? StockSelection
        DaggerStockListFragmentComponent.factory()
            .create(
                stockSelection = stockSelection ?: StockSelection.ALL,
                featureApi = api.getFeature()
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
        binding.stockList.rvItems.adapter = stockListAdapter
        observe(viewModel.stocks) {
            // TODO: load / error
            it.onLoading { }
            it.onSuccess(stockListAdapter::update)
            it.onFailure { }
        }
    }

    companion object {
        private const val BUNDLE_KEY_STOCK_SELECTION = "bundle_key_stock_selection"

        @JvmStatic
        fun newInstance(stockSelection: StockSelection): StockListFragment =
            StockListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_KEY_STOCK_SELECTION, stockSelection)
                }
            }
    }
}
