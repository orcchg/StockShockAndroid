package com.orcchg.yandexcontest.stocklist.di

import android.content.Context
import androidx.room.Room
import com.orcchg.yandexcontest.core.context.api.ApplicationContext
import com.orcchg.yandexcontest.coredi.InternalBindings
import com.orcchg.yandexcontest.stocklist.data.local.IssuerDao
import com.orcchg.yandexcontest.stocklist.data.local.StockListDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
@InternalBindings
object StockListDatabaseModule {

    @Provides
    @Reusable
    fun database(@ApplicationContext context: Context): StockListDatabase =
        Room.databaseBuilder(context, StockListDatabase::class.java, StockListDatabase.DATABASE_NAME).build()

    @Provides
    @Reusable
    fun issuerDao(db: StockListDatabase): IssuerDao = db.issuerDao()
}
