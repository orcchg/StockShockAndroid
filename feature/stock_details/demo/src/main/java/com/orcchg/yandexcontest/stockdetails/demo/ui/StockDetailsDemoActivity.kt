package com.orcchg.yandexcontest.stockdetails.demo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orcchg.yandexcontest.androidutil.observe
import com.orcchg.yandexcontest.androidutil.viewBindings
import com.orcchg.yandexcontest.stockdetails.demo.databinding.StockDetailsDemoActivityBinding
import com.orcchg.yandexcontest.stockdetails.demo.di.DaggerStockDetailsDemoActivityComponent
import com.orcchg.yandexcontest.stockdetails.demo.viewmodel.StockDetailsViewModel
import com.orcchg.yandexcontest.stockdetails.demo.viewmodel.StockDetailsViewModelFactory
import com.orcchg.yandexcontest.stockdetails.fake.di.DaggerFakeStockDetailsFeatureComponent
import javax.inject.Inject

internal class StockDetailsDemoActivity : AppCompatActivity() {

    @Inject lateinit var factory: StockDetailsViewModelFactory
    private val binding by viewBindings(StockDetailsDemoActivityBinding::inflate)
    private val viewModel by viewModels<StockDetailsViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerStockDetailsDemoActivityComponent.factory()
            .create(
                ticker = "YNDX",
                featureApi = DaggerFakeStockDetailsFeatureComponent.create()
            )
            .inject(this)
        super.onCreate(savedInstanceState)

        observe(viewModel.candles) // TODO: add candles on plot
    }
}
