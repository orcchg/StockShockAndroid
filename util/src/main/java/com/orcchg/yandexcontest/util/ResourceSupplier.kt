package com.orcchg.yandexcontest.util

interface ResourceSupplier {

    fun getResIdByKey(key: String): Int
}
