package com.orcchg.yandexcontest.stocklist.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class IssuerDbo(
    @PrimaryKey val ticker: String,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_LOGO_URL) val logoUrl: String? = null,
    @ColumnInfo(name = COLUMN_IS_FAVOURITE) val isFavourite: Boolean = false
) {

    companion object {
        const val COLUMN_IS_FAVOURITE = "isFavourite"
        const val COLUMN_LOGO_URL = "logoUrl"
        const val COLUMN_NAME = "name"
        const val TABLE_NAME = "issuers"
    }
}

data class IssuerFavouriteDbo(
    val ticker: String,
    val isFavourite: Boolean
)
