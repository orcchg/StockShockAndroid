package com.orcchg.yandexcontest.core.parser

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigInteger

@Suppress("Unused")
internal object BigIntegerAdapter {

    @FromJson
    fun fromJson(string: String) = BigInteger(string)

    @ToJson
    fun toJson(value: BigInteger) = value.toString()
}
