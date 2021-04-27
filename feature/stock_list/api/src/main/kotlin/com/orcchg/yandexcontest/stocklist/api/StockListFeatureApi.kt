package com.orcchg.yandexcontest.stocklist.api

import com.orcchg.yandexcontest.coredi.Api

interface StockListFeatureApi : Api {

    fun interactor(): StockListInteractor
}
