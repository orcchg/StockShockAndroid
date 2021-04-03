package com.orcchg.yandexcontest.stocklist.data.finnhub.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orcchg.yandexcontest.stocklist.data.finnhub.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.finnhub.local.model.QuoteDbo

@Database(entities = [IssuerDbo::class, QuoteDbo::class], version = 2)
abstract class StockListDatabase : RoomDatabase() {

    abstract fun issuerDao(): IssuerDao
    abstract fun quoteDao(): QuoteDao

    companion object {
        const val DATABASE_NAME = "StockList.db"
    }
}
