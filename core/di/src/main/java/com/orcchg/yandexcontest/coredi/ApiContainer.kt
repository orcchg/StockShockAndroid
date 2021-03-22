package com.orcchg.yandexcontest.coredi

interface ApiContainer {

    fun <T> getFeature(key: Class<T>): T
}

inline fun <reified T> ApiContainer.getFeature(): T =
    getFeature(T::class.java)
