package com.orcchg.yandexcontest.stocklist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
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

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME} WHERE ${IssuerDbo.COLUMN_ID} LIKE :query")
    fun findIssuers(query: String): Single<List<IssuerDbo>>

    @Update(entity = IssuerDbo::class)
    fun setIssuerFavourite(vararg isFavourite: IssuerFavourite)
}
