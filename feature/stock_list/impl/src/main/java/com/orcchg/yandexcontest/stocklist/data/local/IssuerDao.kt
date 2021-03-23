package com.orcchg.yandexcontest.stocklist.data.local

import androidx.room.*
import com.orcchg.yandexcontest.stocklist.api.model.IssuerFavourite
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo
import io.reactivex.Single

@Dao
interface IssuerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIssuer(issuer: IssuerDbo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addIssuers(issuers: List<IssuerDbo>)

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME}")
    fun issuers(): Single<List<IssuerDbo>>

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME} WHERE ${IssuerDbo.COLUMN_IS_FAVOURITE} = 1")
    fun favouriteIssuers(): Single<List<IssuerDbo>>

    @Query("SELECT * FROM ${IssuerDbo.TABLE_NAME} WHERE ${IssuerDbo.COLUMN_ID} LIKE :query OR ${IssuerDbo.COLUMN_NAME} LIKE :query")
    fun findIssuers(query: String): Single<List<IssuerDbo>>

    @Query("SELECT (${IssuerDbo.COLUMN_IS_FAVOURITE}) FROM ${IssuerDbo.TABLE_NAME} WHERE ${IssuerDbo.COLUMN_ID} = :ticker")
    fun isIssuerFavourite(ticker: String): Boolean

    @Update(entity = IssuerDbo::class)
    fun setIssuerFavourite(vararg isFavourite: IssuerFavourite)
}
