package com.orcchg.yandexcontest.stockdetails.main.ui.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.orcchg.yandexcontest.stockdetails.api.model.StockDetailsTab
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsChartFragment
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsForecastsFragment
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsIdeasFragment
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsNewsFragment
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsOrderBookFragment
import com.orcchg.yandexcontest.stockdetails.main.ui.StockDetailsSummaryFragment
import java.lang.IllegalArgumentException
import javax.inject.Inject

internal class SectionsPagerAdapter @Inject constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = StockDetailsTab.values.size

    override fun createFragment(position: Int): Fragment =
        when (StockDetailsTab.values[position]) {
            StockDetailsTab.CHART -> StockDetailsChartFragment.newInstance()
            StockDetailsTab.SUMMARY -> StockDetailsSummaryFragment.newInstance()
            StockDetailsTab.ORDERBOOK -> StockDetailsOrderBookFragment.newInstance()
            StockDetailsTab.FORECASTS -> StockDetailsForecastsFragment.newInstance()
            StockDetailsTab.IDEAS -> StockDetailsIdeasFragment.newInstance()
            StockDetailsTab.NEWS -> StockDetailsNewsFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid page position")
        }
}
