package com.orcchg.yandexcontest.stockdetails.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orcchg.yandexcontest.stockdetails.data.local.model.CandleDbo.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CandleDbo(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val ticker: String,
    @ColumnInfo(name = COLUMN_PRICE_OPEN) val openPrice: String,
    @ColumnInfo(name = COLUMN_PRICE_MAX) val maxPrice: String,
    @ColumnInfo(name = COLUMN_PRICE_MIN) val minPrice: String,
    @ColumnInfo(name = COLUMN_PRICE_CLOSE) val closePrice: String,
    @ColumnInfo(name = COLUMN_RESOLUTION) val resolution: String,
    @ColumnInfo(name = COLUMN_VOLUME) val volume: Long,
    @ColumnInfo(name = COLUMN_TIMESTAMP) val timestamp: Long
) {

    companion object {
        const val COLUMN_ID = "ticker"
        const val COLUMN_PRICE_OPEN = "openPrice"
        const val COLUMN_PRICE_MAX = "maxPrice"
        const val COLUMN_PRICE_MIN = "minPrice"
        const val COLUMN_PRICE_CLOSE = "closePrice"
        const val COLUMN_RESOLUTION = "resolution"
        const val COLUMN_VOLUME = "volume"
        const val COLUMN_TIMESTAMP = "ts"
        const val TABLE_NAME = "candles"
    }
}
