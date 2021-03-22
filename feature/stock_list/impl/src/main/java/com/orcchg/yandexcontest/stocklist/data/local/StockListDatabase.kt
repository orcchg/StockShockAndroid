package com.orcchg.yandexcontest.stocklist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo

@Database(entities = [IssuerDbo::class], version = 1)
abstract class StockListDatabase : RoomDatabase() {

    abstract fun issuerDao(): IssuerDao

    companion object {
        const val DATABASE_NAME = "StockList.db"
    }
}
