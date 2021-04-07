package com.orcchg.yandexcontest.stockdetails.demo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.formatPriceChange
import com.orcchg.yandexcontest.fake.di.DaggerFakeStockListFeatureComponent
import com.orcchg.yandexcontest.stockdetails.demo.R
import com.orcchg.yandexcontest.stockdetails.demo.databinding.StockDetailsDemoActivityBinding
import com.orcchg.yandexcontest.stockdetails.demo.di.DaggerStockDetailsDemoActivityComponent
import com.orcchg.yandexcontest.stockdetails.demo.ui.view.SectionsPagerAdapter
import com.orcchg.yandexcontest.stockdetails.demo.viewmodel.StockDetailsViewModel
import com.orcchg.yandexcontest.stockdetails.demo.viewmodel.StockDetailsViewModelFactory
import com.orcchg.yandexcontest.stockdetails.fake.di.DaggerFakeStockDetailsFeatureComponent
import com.orcchg.yandexcontest.util.onFailure
import com.orcchg.yandexcontest.util.onLoading
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class StockDetailsDemoActivity : AppCompatActivity() {

    @Inject lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    @Inject lateinit var factory: StockDetailsViewModelFactory
    private lateinit var mediator: TabLayoutMediator
    private val binding by viewBindings(StockDetailsDemoActivityBinding::inflate)
    private val viewModel by viewModels<StockDetailsViewModel> { factory }

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
            tab.text = when (position) {
                0 -> getString(R.string.stock_details_page_chart)
                1 -> getString(R.string.stock_details_page_summary)
                2 -> getString(R.string.stock_details_page_forecasts)
                3 -> getString(R.string.stock_details_page_ideas)
                4 -> getString(R.string.stock_details_page_news)
                else -> throw IllegalArgumentException("Invalid page position")
            }
        }

        observe(viewModel.issuer) {
            it.onSuccess { issuer ->
                binding.tvStockName.text = issuer.name
            }
        }
        observe(viewModel.quote) {
            it.onSuccess { quote ->
                binding.tvStockPrice.text = quote.currentPrice.toString()
                binding.tvStockPriceChange.text = formatPriceChange(quote.currentPrice, quote.priceDayChange)
            }
        }
        observe(viewModel.candles) // TODO: add candles on plot
    }

    override fun onStart() {
        super.onStart()
        binding.viewPager.adapter = sectionsPagerAdapter
        mediator.attach()
    }

    override fun onStop() {
        super.onStop()
        mediator.detach()
        binding.viewPager.adapter = null
    }

    companion object {
        const val TICKER = "AAPL"
    }
}
