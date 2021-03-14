package com.orcchg.direcall.github_repo.di

import com.orcchg.direcall.github_repo.presentation.ui.GithubRepoFragment
import com.orcchg.yandexcontest.network.api.NetworkApi
import com.orcchg.yandexcontest.scheduler.api.di.SchedulerApi
import dagger.Component

@Component(
    modules = [
        GithubRepoModule::class
    ],
    dependencies = [
        NetworkApi::class,
        SchedulerApi::class
    ]
)
interface GithubRepoFragmentComponent {

    @Component.Factory
    interface Factory {

        fun create(
            module: GithubRepoModule,
            networkApi: NetworkApi,
            schedulerApi: SchedulerApi
        ): GithubRepoFragmentComponent
    }

    fun inject(target: GithubRepoFragment)
}

