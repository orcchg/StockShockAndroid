package com.orcchg.yandexcontest.stocklist

import com.orcchg.yandexcontest.core.schedulers.api.SchedulersFactory
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Issuer
import com.orcchg.yandexcontest.stocklist.domain.usecase.FavouriteIssuersChangedUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.FindIssuersByQueryUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetDefaultIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetEmptyQuoteByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetFavouriteIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetIssuerByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalFavouriteIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalIssuerByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetLocalIssuersUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetMissingQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetQuoteByTickerUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.GetRealTimeQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.InvalidateCacheUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.MissingQuotesUseCase
import com.orcchg.yandexcontest.stocklist.domain.usecase.SetIssuerFavouriteUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import java.util.*

class TestStockListInteractor {

    private val findIssuersByQueryUseCase = mockk<FindIssuersByQueryUseCase>()
    private val getDefaultIssuersUseCase = mockk<GetDefaultIssuersUseCase>()
    private val getFavouriteIssuersUseCase = mockk<GetFavouriteIssuersUseCase>()
    private val getIssuerByTickerUseCase = mockk< GetIssuerByTickerUseCase>()
    private val getLocalIssuerByTickerUseCase = mockk<GetLocalIssuerByTickerUseCase>()
    private val getLocalIssuersUseCase = mockk<GetLocalIssuersUseCase>()
    private val getLocalFavouriteIssuersUseCase = mockk<GetLocalFavouriteIssuersUseCase>()
    private val getMissingQuotesUseCase = mockk<GetMissingQuotesUseCase>()
    private val getQuoteByTickerUseCase = mockk<GetQuoteByTickerUseCase>()
    private val getEmptyQuoteByTickerUseCase = mockk<GetEmptyQuoteByTickerUseCase>()
    private val setIssuerFavouriteUseCase = mockk<SetIssuerFavouriteUseCase>()
    private val invalidateCacheUseCase = mockk<InvalidateCacheUseCase>()
    private val favouriteIssuersChangedUseCase = mockk<FavouriteIssuersChangedUseCase>()
    private val getRealTimeQuotesUseCase = mockk<GetRealTimeQuotesUseCase>()
    private val missingQuotesUseCase = mockk<MissingQuotesUseCase>()
    private val schedulersFactory = mockk<SchedulersFactory>()

    private val testScheduler = TestScheduler()

    private val sut: StockListInteractor by lazy(LazyThreadSafetyMode.NONE) {
        StockListInteractorImpl(
            findIssuersByQueryUseCase = findIssuersByQueryUseCase,
            getDefaultIssuersUseCase = getDefaultIssuersUseCase,
            getFavouriteIssuersUseCase = getFavouriteIssuersUseCase,
            getIssuerByTickerUseCase = getIssuerByTickerUseCase,
            getLocalIssuerByTickerUseCase = getLocalIssuerByTickerUseCase,
            getLocalIssuersUseCase = getLocalIssuersUseCase,
            getLocalFavouriteIssuersUseCase = getLocalFavouriteIssuersUseCase,
            getMissingQuotesUseCase = getMissingQuotesUseCase,
            getQuoteByTickerUseCase = getQuoteByTickerUseCase,
            getEmptyQuoteByTickerUseCase = getEmptyQuoteByTickerUseCase,
            setIssuerFavouriteUseCase = setIssuerFavouriteUseCase,
            invalidateCacheUseCase = invalidateCacheUseCase,
            favouriteIssuersChangedUseCase = favouriteIssuersChangedUseCase,
            getRealTimeQuotesUseCase = getRealTimeQuotesUseCase,
            missingQuotesUseCase = missingQuotesUseCase,
            schedulersFactory = schedulersFactory
        )
    }

    @Before
    fun setUp() {
        every { schedulersFactory.io() } returns testScheduler
    }
    
    @Test
    fun `issuer - some ticker, not from cache - correct issuer`() {
        val ticker = "AAPL"
        val currency = Currency.getInstance(Locale.US)
        val issuer = Issuer(
            name = "Apple Inc.",
            ticker = ticker,
            isFavourite = true,
            country = "United States",
            currency = currency
        )
        val observer = TestObserver<Issuer>()

        every { getIssuerByTickerUseCase.source { GetIssuerByTickerUseCase.PARAM_TICKER of ticker } }returns Maybe.fromCallable { issuer }

        sut.issuer(ticker = ticker, forceLocal = false)
            .subscribe(observer)

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.assertValue(issuer)

        verify(getIssuerByTickerUseCase, times(1))
        verifyNoInteractions(getLocalIssuerByTickerUseCase)

        val result = observer.values().firstOrNull()
        Assert.assertNotNull(result)
        assertThat(result!!.name, `is`("Apple Inc."))
        assertThat(result.ticker, `is`(ticker))
        assertThat(result.isFavourite, `is`(true))
        assertThat(result.country, `is`("United States"))
        assertThat(result.currency, `is`(currency))
    }
}
