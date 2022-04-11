package com.orcchg.yandexcontest.core.parser.di

import com.orcchg.yandexcontest.coredi.Api
import com.squareup.moshi.Moshi

interface ParserApi : Api {

    fun moshi(): Moshi
}
