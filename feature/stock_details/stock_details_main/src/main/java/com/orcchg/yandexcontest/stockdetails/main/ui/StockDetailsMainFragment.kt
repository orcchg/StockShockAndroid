package com.orcchg.yandexcontest.stockdetails.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding3.view.clicks
import com.orcchg.yandexcontest.androidutil.argument
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.androidutil.detachableAdapter
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coredi.getFeature
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.stockdetails.api.model.StockDetailsTab
import com.orcchg.yandexcontest.stockdetails.main.R
import com.orcchg.yandexcontest.stockdetails.main.databinding.StockDetailsMainFragmentBinding
import com.orcchg.yandexcontest.stockdetails.main.di.DaggerStockDetailsMainFragmentComponent
import com.orcchg.yandexcontest.stockdetails.main.ui.view.SectionsPagerAdapter
import com.orcchg.yandexcontest.stockdetails.main.viewmodel.StockDetailsMainViewModel
import com.orcchg.yandexcontest.stockdetails.main.viewmodel.StockDetailsMainViewModelFactory
import com.orcchg.yandexcontest.util.onFailure
import com.orcchg.yandexcontest.util.onLoading
import com.orcchg.yandexcontest.util.onSuccess
import javax.inject.Inject

internal class StockDetailsMainFragment : BaseFragment(R.layout.stock_details_main_fragment) {

    @Inject lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    @Inject lateinit var factory: StockDetailsMainViewModelFactory
    private lateinit var mediator: TabLayoutMediator
    private val ticker by argument<String>("ticker")
    private val binding by viewBindings(StockDetailsMainFragmentBinding::bind)
    private val viewModel by viewModels<StockDetailsMainViewModel> { factory }

    override fun onAttach(context: Context) {
        DaggerStockDetailsMainFragmentComponent.factory()
            .create(
                fragment = this,
                ticker = ticker,
                featureApi = api.getFeature(),
                stockListFeatureApi = api.getFeature()
            )
            .inject(this)
        super.onAttach(context)
    }

    @Suppress("AutoDispose", "CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvStockTicker.text = ticker
            ibtnBack.clicks().clickThrottle().subscribe { requireActivity().onBackPressed() }
            ibtnFavourite.clicks().clickThrottle().subscribe {
                viewModel.setIssuerFavourite(ticker)
            }
        }
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

        observe(viewModel.isFavourite) { isFavourite ->
            @DrawableRes val favIcon = if (isFavourite) {
                R.drawable.stock_details_ic_favourite
            } else {
                R.drawable.stock_details_ic_favourite_outline
            }

            binding.ibtnFavourite.setImageResource(favIcon)
        }
        observe(viewModel.issuer) {
            it.onLoading { } // TODO: loading
            it.onSuccess { issuer -> binding.tvStockName.text = issuer.name }
            it.onFailure { } // TODO: fail
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
}
