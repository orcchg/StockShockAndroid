@file:Suppress("UnnecessaryAbstractClass")

package com.orcchg.yandexcontest.base.usecase

import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory

abstract class UseCase(protected val schedulersFactory: SchedulersFactory)
