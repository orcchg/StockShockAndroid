package com.orcchg.yandexcontest.stockdetails.data.wiring

import com.orcchg.yandexcontest.coredi.PublishedNoReasonableAlternatives
import com.orcchg.yandexcontest.stockdetails.data.StockDetailsRepositoryImpl
import com.orcchg.yandexcontest.stockdetails.data.api.StockDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(
    includes = [
        StockDetailsDatabaseModule::class,
        StockDetailsNetworkModule::class
    ]
)
@PublishedNoReasonableAlternatives
interface StockDetailsDataModule {

    @Binds
    @Reusable
    fun repository(impl: StockDetailsRepositoryImpl): StockDetailsRepository
}
