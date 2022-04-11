package com.orcchg.yandexcontest.core.parser.di

import dagger.Component

@Component(
    modules = [
        ParserModule::class
    ]
)
interface ParserComponent : ParserApi
