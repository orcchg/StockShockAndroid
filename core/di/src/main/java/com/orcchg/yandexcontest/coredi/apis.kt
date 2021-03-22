package com.orcchg.yandexcontest.coredi

inline fun <reified T : Api> Map<@JvmSuppressWildcards Class<*>, Api>.get(): T =
    this[T::class.java] as T
