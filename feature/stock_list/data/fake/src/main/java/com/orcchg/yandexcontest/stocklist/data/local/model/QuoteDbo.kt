package com.orcchg.yandexcontest.stocklist.data.local.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class QuoteDbo(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val ticker: String,
    @ColumnInfo(name = COLUMN_PRICE_CURRENT) val currentPrice: String,
    @ColumnInfo(name = COLUMN_PRICE_PREV_CLOSE) val prevClosePrice: String
) {

    companion object {
        const val COLUMN_ID = "ticker"
        const val COLUMN_PRICE_CURRENT = "currentPrice"
        const val COLUMN_PRICE_PREV_CLOSE = "prevClosePrice"
        const val TABLE_NAME = "quotes"
    }
}
