package com.orcchg.yandexcontest.search.demo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.jakewharton.rxbinding3.view.clicks
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.search.demo.R
import com.orcchg.yandexcontest.search.demo.databinding.SearchFlowDemoActivityBinding
import com.orcchg.yandexcontest.search.demo.di.DaggerSearchFlowDemoActivityComponent
import com.orcchg.yandexcontest.search.demo.viewmodel.SearchFlowViewModel
import com.orcchg.yandexcontest.search.demo.viewmodel.SearchFlowViewModelFactory
import javax.inject.Inject

internal class SearchFlowDemoActivity : AppCompatActivity() {

    @Inject lateinit var factory: SearchFlowViewModelFactory
    private val binding by viewBindings(SearchFlowDemoActivityBinding::inflate)
    private val viewModel by viewModels<SearchFlowViewModel> { factory }

    @SuppressLint("AutoDispose", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSearchFlowDemoActivityComponent.create().inject(this)
        super.onCreate(savedInstanceState)

        viewModel.sendSearchRequest("Apple")

        binding.btnSearch.clicks().clickThrottle().subscribe {
            findNavController(R.id.nav_host_fragment).navigate(R.id.search_result_demo_fragment)
        }
    }
}
