package com.orcchg.yandexcontest.stocklist.data.finnhub.local

import android.content.Context
import com.orcchg.yandexcontest.core.context.api.ApplicationContext
import dagger.Reusable
import javax.inject.Inject

@Reusable
class StockListSharedPrefs @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPrefs = context.getSharedPreferences(STORAGE_FILENAME, Context.MODE_PRIVATE)

    companion object {
        private const val STORAGE_FILENAME = "StockListStorage.prefs"
    }
}
