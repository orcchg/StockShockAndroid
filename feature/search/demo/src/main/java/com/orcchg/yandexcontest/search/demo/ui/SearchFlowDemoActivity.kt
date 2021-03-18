package com.orcchg.yandexcontest.search.demo.ui

import androidx.appcompat.app.AppCompatActivity
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.search.demo.R
import com.orcchg.yandexcontest.search.demo.databinding.SearchFlowDemoActivityBinding

internal class SearchFlowDemoActivity : AppCompatActivity(R.layout.search_flow_demo_activity) {

    private val binding by viewBindings(SearchFlowDemoActivityBinding::inflate)
}
