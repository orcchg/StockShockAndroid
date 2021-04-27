package com.orcchg.yandexcontest.main.demo.di

import androidx.fragment.app.FragmentActivity
import com.orcchg.yandexcontest.main.demo.ui.MainDemoActivity
import dagger.BindsInstance
import dagger.Component

@Component
internal interface MainDemoActivityComponent {

    fun inject(target: MainDemoActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: FragmentActivity
        ): MainDemoActivityComponent
    }
}
