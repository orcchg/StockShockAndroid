package com.orcchg.yandexcontest.search.api

import com.orcchg.yandexcontest.coredi.Api

interface SearchFeatureApi : Api {

    fun interactor(): SearchInteractor
}
