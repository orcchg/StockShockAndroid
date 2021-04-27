package com.orcchg.yandexcontest.base.usecase

import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory

abstract class UseCase(protected val schedulersFactory: SchedulersFactory)
