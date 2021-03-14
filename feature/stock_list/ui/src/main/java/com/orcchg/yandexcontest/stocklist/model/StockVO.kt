package com.orcchg.yandexcontest.stocklist.model

data class StockVO(
    val name: String,
    val price: String,
    val priceDailyChange: String,
    val ticker: String,
    val url: String?,
    val isFavourite: Boolean
) {

    fun id(): Long = ticker.hashCode().toLong() // TODO: good hashcode
}
