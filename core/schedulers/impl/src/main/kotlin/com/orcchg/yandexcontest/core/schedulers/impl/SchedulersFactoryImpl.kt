package com.orcchg.yandexcontest.core.schedulers.impl

import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulersFactoryImpl @Inject constructor(
    private val useCaseExecutor: UseCaseThreadExecutor
) : SchedulersFactory {

    override fun io(): Scheduler = Schedulers.io()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun useCase(): Scheduler = Schedulers.from(useCaseExecutor)
}
