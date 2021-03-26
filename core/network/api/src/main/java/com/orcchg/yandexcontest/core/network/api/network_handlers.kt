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

const val RETRY_COUNT = 3

typealias RetryCallback = ((error: Throwable, index: Int) -> Unit)

fun Completable.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, cb: RetryCallback = { _, _ -> }): Completable =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun <reified T> Maybe<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Maybe<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun <reified T> Single<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Single<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun <reified T> Observable<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Observable<T> =
    toFlowable(BackpressureStrategy.MISSING)
        .retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))
        .toObservable()

inline fun <reified T> Flowable<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Flowable<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun backoff(crossinline predicate: (error: Throwable) -> Boolean, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback) =
    { errors: Flowable<Throwable> ->
        errors.zipWith(Flowable.range(1, retryCount + 1), { t, i -> t to i })
            .flatMap { (error, index) ->
                if (predicate(error)) {
                    if (index > retryCount) { // all retries have failed
                        Flowable.error(NetworkRetryFailedException(error))
                    } else {
                        val delay = when (index) {
                            1 -> 1000L // first attempt in a large time interval
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
