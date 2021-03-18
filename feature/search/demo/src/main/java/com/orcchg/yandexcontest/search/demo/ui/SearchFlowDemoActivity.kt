package com.orcchg.yandexcontest.search.demo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.search.demo.R
import com.orcchg.yandexcontest.search.demo.databinding.SearchFlowDemoActivityBinding

internal class SearchFlowDemoActivity : AppCompatActivity(R.layout.search_flow_demo_activity) {

    private val binding by viewBindings(SearchFlowDemoActivityBinding::inflate)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnScrollLeft.clicks().clickThrottle().subscribe {
            binding.sflBottom.scrollBy(-60, 0)
        }
        binding.btnScrollRight.clicks().clickThrottle().subscribe {
            binding.sflBottom.scrollBy(60, 0)
        }
    }
}
