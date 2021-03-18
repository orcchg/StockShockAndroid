package com.orcchg.yandexcontest.util

import io.reactivex.Observable
import io.reactivex.Single

inline fun <reified T> Observable<T>.toSet(): Single<Set<T>> =
    collect({ mutableSetOf() }, { set: MutableSet<T>, next -> set.add(next) }).map { it }

inline fun <reified T> Observable<T>.toListNoDuplicates(): Single<List<T>> =
    toSet().map { it.toList() }
