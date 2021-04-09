package com.orcchg.yandexcontest.main.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding3.view.clicks
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.androidutil.detachableAdapter
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.main.di.DaggerStockPagesComponent
import com.orcchg.yandexcontest.main.ui.databinding.MainStockPagesFragmentBinding
import com.orcchg.yandexcontest.main.ui.view.SectionsPagerAdapter
import com.orcchg.yandexcontest.main.viewmodel.StockPagesViewModel
import timber.log.Timber
import javax.inject.Inject

internal class StockPagesFragment : BaseFragment(R.layout.main_stock_pages_fragment) {

    @Inject lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var mediator: TabLayoutMediator
    private val binding by viewBindings(MainStockPagesFragmentBinding::bind)
    private val viewModel by activityViewModels<StockPagesViewModel>()
    private val pageChangeListener = PageChangeListener()

    override fun onAttach(context: Context) {
        DaggerStockPagesComponent.factory()
            .create(fragment = this)
            .inject(this)
        super.onAttach(context)
    }

    @Suppress("AutoDispose", "CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.viewPager) {
            offscreenPageLimit = StockSelection.values.size
            registerOnPageChangeCallback(pageChangeListener)
        }
        mediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (StockSelection.values[position]) {
                StockSelection.ALL -> getString(R.string.main_tab_stocks)
                StockSelection.FAVOURITE -> getString(R.string.main_tab_favourite)
                else -> throw IllegalStateException("Unsupported stock selection")
            }
        }
        binding.ibtnHelp.clicks().clickThrottle().subscribe { showHelpDialog() }
        with(binding.viewPager) {
            detachableAdapter = sectionsPagerAdapter
            isSaveEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        mediator.attach()
    }

    override fun onStop() {
        super.onStop()
        mediator.detach()
    }

    override fun onDestroyView() {
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeListener)
        super.onDestroyView()
    }

    private fun showHelpDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.main_help_title)
            .setMessage(R.string.main_help_description)
            .show()
    }

    internal inner class PageChangeListener : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            Timber.v("Selected page at index: $position")
            when (val selection = StockSelection.values[position]) {
                StockSelection.ALL, StockSelection.FAVOURITE -> viewModel.onPageSelected(selection)
                else -> throw IllegalStateException("Unsupported stock selection")
            }
        }
    }
}
