package com.orcchg.yandexcontest.stocklist.demo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orcchg.yandexcontest.androidutil.detachableAdapter
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.showToast
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.fake.di.DaggerFakeStockListFeatureComponent
import com.orcchg.yandexcontest.stocklist.ui.adapter.StockListAdapter
import com.orcchg.yandexcontest.stocklist.demo.databinding.StockActivityDemoBinding
import com.orcchg.yandexcontest.stocklist.demo.di.DaggerStockListDemoActivityComponent
import com.orcchg.yandexcontest.stocklist.demo.viewmodel.StockListViewModel
import com.orcchg.yandexcontest.stocklist.demo.viewmodel.StockListViewModelFactory
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class StockListDemoActivity : AppCompatActivity() {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockListViewModelFactory
    private val binding by viewBindings(StockActivityDemoBinding::inflate)
    private val viewModel by viewModels<StockListViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerStockListDemoActivityComponent.factory()
            .create(
                stockSelection = StockSelection.ALL,
                featureApi = DaggerFakeStockListFeatureComponent.create()
            )
            .inject(this)
        super.onCreate(savedInstanceState)

        stockListAdapter.itemClickListener = {
            showToast("Stock ${it.ticker}")
        }
        binding.rvItems.detachableAdapter = stockListAdapter
        observe(viewModel.stocks) {
            it.onSuccess(stockListAdapter::update)
        }
    }
}
