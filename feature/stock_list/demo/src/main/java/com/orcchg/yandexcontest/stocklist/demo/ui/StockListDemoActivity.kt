package com.orcchg.yandexcontest.stocklist.demo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.stocklist.adapter.StockListAdapter
import com.orcchg.yandexcontest.stocklist.demo.R
import com.orcchg.yandexcontest.stocklist.demo.databinding.StockActivityDemoBinding
import com.orcchg.yandexcontest.stocklist.demo.viewmodel.StockListViewModel
import com.orcchg.yandexcontest.stocklist.demo.viewmodel.StockListViewModelFactory
import javax.inject.Inject

class StockListDemoActivity : AppCompatActivity(R.layout.stock_activity_demo) {

    @Inject lateinit var adapter: StockListAdapter
    @Inject lateinit var factory: StockListViewModelFactory
    private val binding by viewBindings(StockActivityDemoBinding::inflate)
    private val viewModel by viewModels<StockListViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvItems.adapter = adapter
        observe(viewModel.stocks) { adapter.update(it.getOrThrow()) }
    }
}
