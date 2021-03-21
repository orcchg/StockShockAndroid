package com.orcchg.yandexcontest.main.ui.ui

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.ui.R
import com.orcchg.yandexcontest.main.ui.databinding.MainStockPagesFragmentBinding
import com.orcchg.yandexcontest.main.ui.ui.view.SectionsPagerAdapter
import javax.inject.Inject

internal class StockPagesFragment : BaseFragment(R.layout.main_stock_pages_fragment) {

    @Inject lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private val binding by viewBindings(MainStockPagesFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: inject
        super.onCreate(savedInstanceState)

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (StockSelection.values[position]) {
                StockSelection.ALL -> getString(R.string.main_tab_stocks)
                StockSelection.FAVOURITE -> getString(R.string.main_tab_favourite)
                else -> ""
            }
        }.attach()
    }
}
