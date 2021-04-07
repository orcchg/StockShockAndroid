package com.orcchg.yandexcontest.stockdetails.demo.ui.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsChartDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsForecastsDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsIdeasDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsNewsDemoFragment
import com.orcchg.yandexcontest.stockdetails.demo.ui.StockDetailsSummaryDemoFragment
import java.lang.IllegalArgumentException
import javax.inject.Inject

internal class SectionsPagerAdapter @Inject constructor(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> StockDetailsChartDemoFragment.newInstance()
            1 -> StockDetailsSummaryDemoFragment.newInstance()
            2 -> StockDetailsForecastsDemoFragment.newInstance()
            3 -> StockDetailsIdeasDemoFragment.newInstance()
            4 -> StockDetailsNewsDemoFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid page position")
        }
}
