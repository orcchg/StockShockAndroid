package com.orcchg.yandexcontest.main.demo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.fake.di.DaggerFakeStockListFeatureComponent
import com.orcchg.yandexcontest.main.demo.R
import com.orcchg.yandexcontest.main.demo.databinding.MainStockListDemoFragmentBinding
import com.orcchg.yandexcontest.main.demo.di.DaggerStockListDemoFragmentComponent
import com.orcchg.yandexcontest.main.demo.viewmodel.StockListViewModel
import com.orcchg.yandexcontest.main.demo.viewmodel.StockListViewModelFactory
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import javax.inject.Inject

internal class StockListDemoFragment : Fragment(R.layout.main_stock_list_demo_fragment) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockListViewModelFactory
    private val binding by viewBindings(MainStockListDemoFragmentBinding::bind)
    private val viewModel by viewModels<StockListViewModel> { factory }

    override fun onAttach(context: Context) {
        DaggerStockListDemoFragmentComponent.factory()
            .create(featureApi = DaggerFakeStockListFeatureComponent.create())
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.stockList.rvItems.adapter = stockListAdapter
        observe(viewModel.stocks) { stockListAdapter.update(it.getOrThrow()) }
    }

    companion object {
        @JvmStatic
        fun newInstance(): StockListDemoFragment = StockListDemoFragment()
    }
}
