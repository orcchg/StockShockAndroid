package com.orcchg.yandexcontest.androidutil

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.clickThrottle(timeout: Long = 200L): Observable<T> =
    throttleFirst(timeout, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())

fun <T> Observable<T>.inputDebounce(timeout: Long = 150L): Observable<T> =
    debounce(timeout, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
