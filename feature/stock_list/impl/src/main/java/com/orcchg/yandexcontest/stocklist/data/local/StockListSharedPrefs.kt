package com.orcchg.yandexcontest.stocklist.data.local

import android.content.Context
import androidx.core.content.edit
import com.orcchg.yandexcontest.core.context.api.ApplicationContext
import javax.inject.Inject

class StockListSharedPrefs @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPrefs = context.getSharedPreferences(STORAGE_FILENAME, Context.MODE_PRIVATE)

    fun getDefaultIssuersCacheTimestamp(): Long =
        sharedPrefs.getLong(SP_KEY_CACHE_TS_ISSUERS, 0L)

    fun recordDefaultIssuersCacheTimestamp(ts: Long) {
        sharedPrefs.edit { putLong(SP_KEY_CACHE_TS_ISSUERS, ts) }
    }

    companion object {
        private const val STORAGE_FILENAME = "SuperAppCameraStorage.prefs"

        private const val SP_KEY_CACHE_TS_ISSUERS = "sp_key_cache_ts_issuers"
    }
}
