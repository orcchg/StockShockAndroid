package com.orcchg.yandexcontest.stocklist.data.wiring

import com.orcchg.yandexcontest.stocklist.data.api.StockListDataApi
import dagger.Component

@Component
interface StockListDataComponent : StockListDataApi {
}
