package com.orcchg.yandexcontest.stockdetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orcchg.yandexcontest.stockdetails.data.local.model.CandleDbo

@Database(entities = [CandleDbo::class], version = 1)
abstract class StockDetailsDatabase : RoomDatabase() {

    abstract fun candleDao(): CandleDao

    companion object {
        const val DATABASE_NAME = "StockDetails.db"
    }
}
