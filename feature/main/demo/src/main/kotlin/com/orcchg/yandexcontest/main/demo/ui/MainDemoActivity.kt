package com.orcchg.yandexcontest.main.demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.orcchg.yandexcontest.androidutil.detachableAdapter
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.main.demo.R
import com.orcchg.yandexcontest.main.demo.databinding.MainDemoActivityBinding
import com.orcchg.yandexcontest.main.demo.di.DaggerMainDemoActivityComponent
import com.orcchg.yandexcontest.main.demo.ui.view.SectionsPagerAdapter
import javax.inject.Inject

internal class MainDemoActivity : AppCompatActivity() {

    @Inject lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var mediator: TabLayoutMediator
    private val binding by viewBindings(MainDemoActivityBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMainDemoActivityComponent.factory()
            .create(activity = this)
            .inject(this)
        super.onCreate(savedInstanceState)

        with(binding.viewPager) {
            detachableAdapter = sectionsPagerAdapter
            isSaveEnabled = false
        }
        mediator = TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (StockSelection.values[position]) {
                StockSelection.ALL -> getString(R.string.main_tab_stocks)
                StockSelection.FAVOURITE -> getString(R.string.main_tab_favourite)
                else -> throw IllegalStateException("Unsupported stock selection")
            }
        }
            .also { it.attach() }
    }

    override fun onDestroy() {
        mediator.detach()
        super.onDestroy()
    }
}
