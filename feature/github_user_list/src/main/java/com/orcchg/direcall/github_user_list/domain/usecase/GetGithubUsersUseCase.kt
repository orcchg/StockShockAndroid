package com.orcchg.direcall.github_user_list.domain.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.base.usecase.SingleUseCase
import com.orcchg.direcall.github_user_list.data.repository.GithubUserListRepository
import com.orcchg.direcall.github_user_list.domain.model.GithubUser
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import io.reactivex.Single
import javax.inject.Inject

class GetGithubUsersUseCase @Inject constructor(
    private val repository: GithubUserListRepository,
    schedulersFactory: SchedulersFactory
) : SingleUseCase<List<GithubUser>>(schedulersFactory) {

    override fun sourceImpl(params: Params): Single<List<GithubUser>> =
        repository.users()
}
