package com.orcchg.yandexcontest.stocklist.data.fake.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orcchg.yandexcontest.stocklist.data.fake.local.model.IssuerDbo
import io.reactivex.Single

@Dao
interface IssuerDao {

    // keep isFavourite, issuer doesn't change ever
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIssuers(issuers: List<IssuerDbo>)

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME}")
    fun issuers(): Single<List<IssuerDbo>>

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME} WHERE ${IssuerDbo.COLUMN_IS_FAVOURITE} = 1")
    fun favouriteIssuers(): Single<List<IssuerDbo>>
}
