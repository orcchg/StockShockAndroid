package com.orcchg.yandexcontest.stockdetails.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orcchg.yandexcontest.stockdetails.data.local.model.CandleDbo
import io.reactivex.Single

@Dao
interface CandleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCandle(candle: CandleDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCandles(candles: List<CandleDbo>)

    @Query("SELECT * FROM ${CandleDbo.TABLE_NAME} WHERE ${CandleDbo.COLUMN_ID} = :ticker")
    fun candles(ticker: String): Single<List<CandleDbo>>

    @Query("DELETE FROM ${CandleDbo.TABLE_NAME}")
    fun clear()

    @Query("DELETE FROM ${CandleDbo.TABLE_NAME} WHERE ${CandleDbo.COLUMN_ID} = :ticker")
    fun clear(ticker: String)
}
