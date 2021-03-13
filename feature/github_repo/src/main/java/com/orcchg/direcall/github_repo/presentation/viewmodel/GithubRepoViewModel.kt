package com.orcchg.direcall.github_repo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.direcall.ui_core_lib.AutoDisposeViewModel
import com.orcchg.direcall.github_repo.domain.model.GithubRepo
import com.orcchg.direcall.github_repo.domain.usecase.GetGithubReposUseCase
import com.uber.autodispose.autoDispose
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class GithubRepoViewModel @Inject constructor(
    @Named("login") private val login: String,
    private val getGithubReposUseCase: GetGithubReposUseCase
) : AutoDisposeViewModel() {

    val repos: LiveData<List<GithubRepo>> by lazy(LazyThreadSafetyMode.NONE) {
        val liveData = MutableLiveData<List<GithubRepo>>()
        getGithubReposUseCase.source { "login" of login }
            .autoDispose(this)
            .subscribe({ liveData.value = it }, Timber::e)
        liveData
    }
}
