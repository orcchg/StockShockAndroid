package com.orcchg.yandexcontest.core.network.api

import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import kotlin.math.pow

const val RETRY_COUNT = 12

typealias RetryCallback = ((error: Throwable, index: Int) -> Unit)

fun Completable.handleHttpError(errorCode: Int, cb: RetryCallback = { _, _ -> }): Completable =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, cb))

inline fun <reified T> Maybe<T>.handleHttpError(errorCode: Int, crossinline cb: RetryCallback = { _, _ -> }): Maybe<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, cb))

inline fun <reified T> Single<T>.handleHttpError(errorCode: Int, crossinline cb: RetryCallback = { _, _ -> }): Single<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, cb))

inline fun <reified T> Observable<T>.handleHttpError(errorCode: Int, crossinline cb: RetryCallback = { _, _ -> }): Observable<T> =
    toFlowable(BackpressureStrategy.MISSING)
        .retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, cb))
        .toObservable()

inline fun <reified T> Flowable<T>.handleHttpError(errorCode: Int, crossinline cb: RetryCallback = { _, _ -> }): Flowable<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, cb))

inline fun backoff(crossinline predicate: (error: Throwable) -> Boolean, crossinline cb: RetryCallback) =
    { errors: Flowable<Throwable> ->
        errors.zipWith(Flowable.range(1, RETRY_COUNT + 1), { t, i -> t to i })
            .flatMap { (error, index) ->
                if (predicate(error)) {
                    if (index > RETRY_COUNT) { // all retries have failed
                        Flowable.error(NetworkRetryFailedException(error))
                    } else {
                        val delay = when (index) {
                            1 -> 5000L // first attempt in a large time interval
                            else -> minOf((54 * 1.8.pow(index)).toLong(), 1000L)
                        }
                        Flowable.timer(delay, TimeUnit.MILLISECONDS)
                            .doOnComplete { cb.invoke(error, index) }
                    }
                } else {
                    Flowable.error(error)
                }
            }
    }
