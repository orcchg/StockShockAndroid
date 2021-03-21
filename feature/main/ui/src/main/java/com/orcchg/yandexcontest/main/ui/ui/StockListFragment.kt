package com.orcchg.yandexcontest.main.ui.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.ui.R
import com.orcchg.yandexcontest.main.ui.databinding.MainStockListFragmentBinding
import com.orcchg.yandexcontest.main.ui.viewmodel.StockListViewModel
import com.orcchg.yandexcontest.main.ui.viewmodel.StockListViewModelFactory
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class StockListFragment : BaseFragment(R.layout.main_stock_list_fragment) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockListViewModelFactory
    private val binding by viewBindings(MainStockListFragmentBinding::bind)
    private val viewModel by viewModels<StockListViewModel> { factory }

    override fun onAttach(context: Context) {
        // TODO: inject
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockListAdapter.itemClickListener = {
            // TODO: stock item click
        }
        binding.stockList.rvItems.adapter = stockListAdapter
        observe(viewModel.stocks) {
            // TODO: loading/error
            it.onSuccess(stockListAdapter::update)
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
