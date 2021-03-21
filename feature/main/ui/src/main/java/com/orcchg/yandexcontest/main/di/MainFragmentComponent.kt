package com.orcchg.yandexcontest.main.di

import com.orcchg.yandexcontest.main.ui.MainFragment
import dagger.Component

@Component
internal interface MainFragmentComponent {

    fun inject(target: MainFragment)
}
