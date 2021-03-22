package com.orcchg.yandexcontest.main.demo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.fake.di.DaggerFakeStockListFeatureComponent
import com.orcchg.yandexcontest.main.demo.R
import com.orcchg.yandexcontest.main.demo.databinding.MainStockListDemoFragmentBinding
import com.orcchg.yandexcontest.main.demo.di.DaggerStockListDemoFragmentComponent
import com.orcchg.yandexcontest.main.demo.viewmodel.StockListViewModel
import com.orcchg.yandexcontest.main.demo.viewmodel.StockListViewModelFactory
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class StockListDemoFragment : Fragment(R.layout.main_stock_list_demo_fragment) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockListViewModelFactory
    private val binding by viewBindings(MainStockListDemoFragmentBinding::bind)
    private val viewModel by viewModels<StockListViewModel> { factory }

    override fun onAttach(context: Context) {
        val stockSelection = arguments?.getSerializable(BUNDLE_KEY_STOCK_SELECTION) as? StockSelection
        DaggerStockListDemoFragmentComponent.factory()
            .create(
                stockSelection = stockSelection ?: StockSelection.ALL,
                featureApi = DaggerFakeStockListFeatureComponent.create()
            )
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockListAdapter.itemClickListener = {
            Toast.makeText(context, "Stock ${it.ticker}", Toast.LENGTH_SHORT).show()
        }
        binding.rvItems.adapter = stockListAdapter
        observe(viewModel.stocks) {
            it.onSuccess(stockListAdapter::update)
        }
    }

    companion object {
        private const val BUNDLE_KEY_STOCK_SELECTION = "bundle_key_stock_selection"

        @JvmStatic
        fun newInstance(stockSelection: StockSelection): StockListDemoFragment =
            StockListDemoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_KEY_STOCK_SELECTION, stockSelection)
                }
            }
    }
}
