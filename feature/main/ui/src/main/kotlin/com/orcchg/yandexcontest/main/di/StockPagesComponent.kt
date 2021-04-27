package com.orcchg.yandexcontest.main.di

import androidx.fragment.app.Fragment
import com.orcchg.yandexcontest.main.ui.StockPagesFragment
import dagger.BindsInstance
import dagger.Component

@Component
internal interface StockPagesComponent {

    fun inject(target: StockPagesFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): StockPagesComponent
    }
}
