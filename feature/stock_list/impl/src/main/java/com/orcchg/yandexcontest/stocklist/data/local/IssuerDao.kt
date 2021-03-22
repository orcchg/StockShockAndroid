package com.orcchg.yandexcontest.stocklist.data.local

import androidx.room.*
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerFavouriteDbo
import io.reactivex.Single

@Dao
interface IssuerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIssuer(issuer: IssuerDbo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIssuers(issuers: List<IssuerDbo>)

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME}")
    fun issuers(): Single<List<IssuerDbo>>

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME} WHERE ${IssuerDbo.COLUMN_IS_FAVOURITE} = 1")
    fun favouriteIssuers(): Single<List<IssuerDbo>>

    @Query("SELECT (${IssuerDbo.COLUMN_IS_FAVOURITE}) FROM ${IssuerDbo.TABLE_NAME} WHERE ${IssuerDbo.COLUMN_ID} = :ticker")
    fun isIssuerFavourite(ticker: String): Boolean

    @Update(entity = IssuerDbo::class)
    fun setIssuerFavourite(vararg isFavourite: IssuerFavouriteDbo)
}
