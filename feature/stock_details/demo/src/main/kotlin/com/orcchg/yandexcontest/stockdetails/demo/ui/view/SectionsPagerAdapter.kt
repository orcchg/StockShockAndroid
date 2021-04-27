package com.orcchg.yandexcontest.stockdetails.demo.ui.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.orcchg.yandexcontest.stockdetails.api.model.StockDetailsTab
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsChartDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsForecastsDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsIdeasDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsNewsDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsOrderBookDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsSummaryDemoFragment
import java.lang.IllegalArgumentException
import javax.inject.Inject

internal class SectionsPagerAdapter @Inject constructor(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = StockDetailsTab.values.size

    override fun createFragment(position: Int): Fragment =
        when (StockDetailsTab.values[position]) {
            StockDetailsTab.CHART -> StockDetailsChartDemoFragment.newInstance()
            StockDetailsTab.SUMMARY -> StockDetailsSummaryDemoFragment.newInstance()
            StockDetailsTab.ORDERBOOK -> StockDetailsOrderBookDemoFragment.newInstance()
            StockDetailsTab.FORECASTS -> StockDetailsForecastsDemoFragment.newInstance()
            StockDetailsTab.IDEAS -> StockDetailsIdeasDemoFragment.newInstance()
            StockDetailsTab.NEWS -> StockDetailsNewsDemoFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid page position")
        }
}
