package com.orcchg.yandexcontest.stocklist.data.fake.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orcchg.yandexcontest.stocklist.data.fake.local.model.QuoteDbo
import io.reactivex.Maybe

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuotes(quotes: List<QuoteDbo>)

    @Query("SELECT * FROM ${QuoteDbo.TABLE_NAME} WHERE ${QuoteDbo.COLUMN_ID} = :ticker")
    fun quote(ticker: String): Maybe<QuoteDbo>
}
