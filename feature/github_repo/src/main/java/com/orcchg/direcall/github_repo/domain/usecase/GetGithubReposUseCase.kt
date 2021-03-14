package com.orcchg.direcall.github_repo.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.processSingle
import com.orcchg.yandexcontest.base.usecase.SingleUseCase
import com.orcchg.direcall.github_repo.data.repository.GithubRepoRepository
import com.orcchg.direcall.github_repo.domain.model.GithubRepo
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import io.reactivex.Single
import javax.inject.Inject

class GetGithubReposUseCase @Inject constructor(
    private val repository: GithubRepoRepository,
    schedulersFactory: SchedulersFactory
) : SingleUseCase<List<GithubRepo>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Single<List<GithubRepo>> =
        params.processSingle("login", repository::repos)
}
