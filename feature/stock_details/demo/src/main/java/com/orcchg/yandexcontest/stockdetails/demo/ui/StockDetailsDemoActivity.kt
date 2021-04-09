package com.orcchg.yandexcontest.stockdetails.demo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.orcchg.yandexcontest.androidutil.detachableAdapter
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.fake.di.DaggerFakeStockListFeatureComponent
import com.orcchg.yandexcontest.stockdetails.api.model.StockDetailsTab
import com.orcchg.yandexcontest.stockdetails.demo.R
import com.orcchg.yandexcontest.stockdetails.demo.databinding.StockDetailsDemoActivityBinding
import com.orcchg.yandexcontest.stockdetails.demo.di.DaggerStockDetailsDemoActivityComponent
import com.orcchg.yandexcontest.stockdetails.demo.ui.view.SectionsPagerAdapter
import com.orcchg.yandexcontest.stockdetails.demo.viewmodel.StockDetailsViewModel
import com.orcchg.yandexcontest.stockdetails.demo.viewmodel.StockDetailsViewModelFactory
import com.orcchg.yandexcontest.stockdetails.fake.di.DaggerFakeStockDetailsFeatureComponent
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class StockDetailsDemoActivity : AppCompatActivity() {

    @Inject lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    @Inject lateinit var factory: StockDetailsViewModelFactory
    private lateinit var mediator: TabLayoutMediator
    private val binding by viewBindings(StockDetailsDemoActivityBinding::inflate)
    private val viewModel by viewModels<StockDetailsViewModel> { factory }

    @Suppress("AutoDispose", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerStockDetailsDemoActivityComponent.factory()
            .create(
                activity = this,
                ticker = TICKER,
                featureApi = DaggerFakeStockDetailsFeatureComponent.create(),
                stockListFeatureApi = DaggerFakeStockListFeatureComponent.create()
            )
            .inject(this)
        super.onCreate(savedInstanceState)

        binding.tvStockTicker.text = TICKER
        mediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (StockDetailsTab.values[position]) {
                StockDetailsTab.CHART -> getString(R.string.stock_details_page_chart)
                StockDetailsTab.SUMMARY -> getString(R.string.stock_details_page_summary)
                StockDetailsTab.ORDERBOOK -> getString(R.string.stock_details_page_orderbook)
                StockDetailsTab.FORECASTS -> getString(R.string.stock_details_page_forecasts)
                StockDetailsTab.IDEAS -> getString(R.string.stock_details_page_ideas)
                StockDetailsTab.NEWS -> getString(R.string.stock_details_page_news)
                else -> throw IllegalArgumentException("Invalid page position")
            }
        }

        observe(viewModel.issuer) {
            it.onSuccess { issuer ->
                binding.tvStockName.text = issuer.name
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.viewPager.detachableAdapter = sectionsPagerAdapter
        mediator.attach()
    }

    override fun onStop() {
        super.onStop()
        mediator.detach()
    }

    companion object {
        const val TICKER = "AAPL"
    }
}
