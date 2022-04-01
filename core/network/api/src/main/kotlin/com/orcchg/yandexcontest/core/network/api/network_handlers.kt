package com.orcchg.yandexcontest.core.network.api

import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

const val RETRY_COUNT = 1

typealias RetryCallback = ((error: Throwable, index: Int) -> Unit)

fun Completable.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, cb: RetryCallback = { _, _ -> }): Completable =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun <reified T> Maybe<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Maybe<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun <T> Single<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Single<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun <T> Observable<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Observable<T> =
    toFlowable(BackpressureStrategy.MISSING)
        .retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))
        .toObservable()

inline fun <reified T> Flowable<T>.handleHttpError(errorCode: Int, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback = { _, _ -> }): Flowable<T> =
    retryWhen(backoff(predicate = { error -> error is HttpException && error.code() == errorCode }, retryCount, cb))

inline fun backoff(crossinline predicate: (error: Throwable) -> Boolean, retryCount: Int = RETRY_COUNT, crossinline cb: RetryCallback) =
    { errors: Flowable<Throwable> ->
        errors.zipWith(Flowable.range(1, retryCount + 1)) { t, i -> t to i }
            .flatMap { (error, attempt) ->
                if (predicate(error)) {
                    if (attempt > retryCount) { // all retries have failed
                        Flowable.error(NetworkRetryFailedException(error))
                    } else {
                        Flowable.timer(1000L, TimeUnit.MILLISECONDS)
                            .doOnComplete { cb.invoke(error, attempt) }
                    }
                } else {
                    Flowable.error(error)
                }
            }
    }
