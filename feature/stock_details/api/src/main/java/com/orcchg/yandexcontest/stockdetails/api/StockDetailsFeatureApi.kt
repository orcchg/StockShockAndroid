package com.orcchg.yandexcontest.stockdetails.api

import com.orcchg.yandexcontest.coredi.Api

interface StockDetailsFeatureApi : Api {

    fun interactor(): StockDetailsInteractor
}
