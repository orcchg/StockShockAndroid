package com.orcchg.yandexcontest.stocklist

import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.StockSelectionSupplier
import javax.inject.Inject

class StockSelectionLocalSupplier @Inject constructor(
    private val interactor: StockListInteractor
) : StockSelectionSupplier {

    override fun stockSelection(ticker: String): StockSelection =
        interactor.stockSelection(ticker)
}
