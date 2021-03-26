package com.orcchg.yandexcontest.stocklist.data.local

import android.content.Context
import com.orcchg.yandexcontest.core.context.api.ApplicationContext
import javax.inject.Inject

class StockListSharedPrefs @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPrefs = context.getSharedPreferences(STORAGE_FILENAME, Context.MODE_PRIVATE)

    companion object {
        private const val STORAGE_FILENAME = "StockListStorage.prefs"
    }
}
