package com.orcchg.yandexcontest.stocklist.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orcchg.yandexcontest.stocklist.data.local.model.IssuerDbo.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class IssuerDbo(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val ticker: String,
    @ColumnInfo(name = COLUMN_COUNTRY) val country: String? = null,
    @ColumnInfo(name = COLUMN_CURRENCY) val currency: String? = null,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_LOGO_URL) val logoUrl: String? = null,
    @ColumnInfo(name = COLUMN_IS_FAVOURITE) val isFavourite: Boolean = false
) {

    companion object {
        const val COLUMN_ID = "ticker"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_CURRENCY = "currency"
        const val COLUMN_LOGO_URL = "logoUrl"
        const val COLUMN_NAME = "name"
        const val COLUMN_IS_FAVOURITE = "isFavourite"
        const val TABLE_NAME = "issuers"
    }
}
