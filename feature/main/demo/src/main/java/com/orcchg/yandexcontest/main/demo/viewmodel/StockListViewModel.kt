package com.orcchg.yandexcontest.main.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.stocklist.api.model.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.convert.StockVoConverter
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

internal class StockListViewModel @Inject constructor(
    private val interactor: StockListInteractor,
    private val stockSelection: StockSelection,
    private val stockVoConverter: StockVoConverter
) : AutoDisposeViewModel() {

    private val _stocks = MutableLiveData<List<Stock>>() // actual stocks
    private val _stocksVo by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<StockVO>>>()
        val source = when (stockSelection) {
            StockSelection.ALL -> interactor.stocks()
            StockSelection.FAVOURITE -> interactor.favouriteStocks()
            else -> throw IllegalStateException("Unsupported stock selection")
        }
        source
            .doOnSubscribe { data.value = DataState.loading() }
            .doOnSuccess { _stocks.value = it }
            .map(stockVoConverter::convertList)
            .autoDispose(this)
            .subscribe(
                {
                    data.value = DataState.success(it)
                },
                {
                    Timber.e(it)
                    data.value = DataState.failure(it)
                }
            )
        data
    }
    internal val stocks: LiveData<DataState<List<StockVO>>> by lazy(LazyThreadSafetyMode.NONE) { _stocksVo }

    init {
        interactor.realTimeQuotes
            .doOnNext { Timber.v("Apply rt-quotes: ${it.joinToString { s -> "[${s.ticker}:${s.currentPrice}:${s.prevClosePrice}]" }}") }
            .flatMapSingle { quotes ->
                val currentStocks = mutableListOf<Stock>()
                    .apply { _stocks.value?.let(this::addAll) }

                var source: Single<List<StockVO>> = Single.just(emptyList())

                if (currentStocks.isNotEmpty()) {
                    var modified = false
                    quotes.forEach { quote ->
                        /**
                         * For each incoming [Quote] tries to update prices for corresponding
                         * stock in the current list of [StockVO]s. [Quote] may not contain
                         * determined [Quote.prevClosePrice] and the latter will default to [Money.ZERO].
                         * If so, [StockVO.priceDailyChange] will be calculated as a difference between
                         * [Quote.currentPrice] and previous close price of the corresponding [StockVO].
                         */
                        currentStocks.indexOfFirst { it.ticker == quote.ticker }
                            .takeIf { it != -1 }
                            ?.let { index ->
                                val stock = currentStocks[index]
                                val prevClosePrice = quote.prevClosePrice.takeUnless { it.isZero() } ?: stock.prevClosePrice
                                stock.copy(
                                    price = quote.currentPrice,
                                    priceDailyChange = quote.currentPrice - prevClosePrice
                                ) to index
                            }
                            ?.let { (stock, index) -> currentStocks[index] = stock }
                            ?.also { modified = true }
                    } // loop over quotes
                    if (modified) {
                        source = Single.just(currentStocks.map(stockVoConverter::convert))
                    }
                }
                source
            }
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(this)
            .subscribe({ stocks -> _stocksVo.value = DataState.success(stocks) }, Timber::e)
    }
}
