package com.orcchg.yandexcontest.stockdetails.data.wiring

import android.content.Context
import androidx.room.Room
import com.orcchg.yandexcontest.core.context.api.ApplicationContext
import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.stockdetails.data.local.CandleDao
import com.orcchg.yandexcontest.stockdetails.data.local.StockDetailsDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
@InternalBindings
object StockDetailsDatabaseModule {

    @Provides
    @Reusable
    fun database(@ApplicationContext context: Context): StockDetailsDatabase =
        Room.databaseBuilder(context, StockDetailsDatabase::class.java, StockDetailsDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Reusable
    fun candleDao(db: StockDetailsDatabase): CandleDao = db.candleDao()
}
