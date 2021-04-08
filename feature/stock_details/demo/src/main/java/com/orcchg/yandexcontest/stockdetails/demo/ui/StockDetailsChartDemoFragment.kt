package com.orcchg.yandexcontest.stockdetails.demo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.showToast
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.formatPriceChange
import com.orcchg.yandexcontest.design.rx_ext.checkedChanges
import com.orcchg.yandexcontest.stockdetails.demo.R
import com.orcchg.yandexcontest.stockdetails.demo.databinding.StockDetailsChartDemoFragmentBinding
import com.orcchg.yandexcontest.stockdetails.demo.viewmodel.StockDetailsViewModel
import com.orcchg.yandexcontest.util.onSuccess

internal class StockDetailsChartDemoFragment : Fragment(R.layout.stock_details_chart_demo_fragment) {

    private val binding by viewBindings(StockDetailsChartDemoFragmentBinding::bind)
    private val viewModel by activityViewModels<StockDetailsViewModel>()

    @Suppress("AutoDispose", "CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            radioGroup.checkedChanges().skipInitialValue().subscribe { (id, isChecked) ->
                when (id) {
                    btnPlotDay.id -> context?.showToast("Daily: $isChecked")
                    btnPlotWeek.id -> context?.showToast("Weekly: $isChecked")
                    btnPlotMonth.id -> context?.showToast("Monthly: $isChecked")
                    btnPlotSixMonths.id -> context?.showToast("Half-year: $isChecked")
                    btnPlotYear.id -> context?.showToast("Annual: $isChecked")
                    btnPlotAll.id -> context?.showToast("All history: $isChecked")
                }
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

    companion object {
        @JvmStatic
        fun newInstance(): StockDetailsChartDemoFragment = StockDetailsChartDemoFragment()
    }
}
