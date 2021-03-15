package com.orcchg.yandexcontest.stocklist.demo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.fake.di.DaggerFakeStockListFeatureComponent
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import com.orcchg.yandexcontest.stocklist.demo.R
import com.orcchg.yandexcontest.stocklist.demo.databinding.StockActivityDemoBinding
import com.orcchg.yandexcontest.stocklist.demo.di.DaggerStockListDemoActivityComponent
import com.orcchg.yandexcontest.stocklist.demo.viewmodel.StockListViewModel
import com.orcchg.yandexcontest.stocklist.demo.viewmodel.StockListViewModelFactory
import javax.inject.Inject

internal class StockListDemoActivity : AppCompatActivity(R.layout.stock_activity_demo) {

    @Inject lateinit var stockListAdapter: StockListAdapter
    @Inject lateinit var factory: StockListViewModelFactory
    private val binding by viewBindings(StockActivityDemoBinding::inflate)
    private val viewModel by viewModels<StockListViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerStockListDemoActivityComponent.factory()
            .create(featureApi = DaggerFakeStockListFeatureComponent.create())
            .inject(this)
        super.onCreate(savedInstanceState)

        stockListAdapter.itemClickListener = {
            Toast.makeText(this, "Stock ${it.ticker}", Toast.LENGTH_SHORT).show()
        }
        binding.stockList.rvItems.adapter = stockListAdapter
        observe(viewModel.stocks) { stockListAdapter.update(it.getOrThrow()) }
    }
}