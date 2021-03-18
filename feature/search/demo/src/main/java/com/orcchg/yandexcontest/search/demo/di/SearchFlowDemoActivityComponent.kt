package com.orcchg.yandexcontest.search.demo.di

import com.orcchg.yandexcontest.search.demo.ui.SearchFlowDemoActivity
import dagger.Component

@Component
internal interface SearchFlowDemoActivityComponent {

    fun inject(target: SearchFlowDemoActivity)
}
