package com.orcchg.yandexcontest.base.usecase

import com.orcchg.yandexcontest.base.Params
import com.orcchg.yandexcontest.scheduler.api.SchedulersFactory
import io.reactivex.Maybe
import io.reactivex.Scheduler

abstract class MaybeUseCase<T>(
    schedulersFactory: SchedulersFactory
) : UseCase(schedulersFactory) {

    abstract fun sourceImpl(params: Params = Params.EMPTY): Maybe<T>

    inline fun sourceImpl(initializer: Params.() -> Unit): Maybe<T> =
        sourceImpl(Params().apply(initializer))

    fun source(params: Params = Params.EMPTY): Maybe<T> =
        sourceImpl(params)
            .subscribeOn(schedulersFactory.useCase())
            .observeOn(schedulersFactory.main())

    inline fun source(initializer: Params.() -> Unit): Maybe<T> =
        source(Params().apply(initializer))

    fun source(scheduler: Scheduler, params: Params = Params.EMPTY): Maybe<T> =
        sourceImpl(params).subscribeOn(scheduler)

    inline fun source(scheduler: Scheduler, initializer: Params.() -> Unit): Maybe<T> =
        source(scheduler, Params().apply(initializer))
}
