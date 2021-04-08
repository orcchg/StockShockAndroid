package com.orcchg.yandexcontest.stockdetails.api.model

enum class StockDetailsTab {
    CHART,
    SUMMARY,
    ORDERBOOK,
    FORECASTS,
    IDEAS,
    NEWS;

    companion object {
        val values = values()
    }
}
