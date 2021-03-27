package com.orcchg.yandexcontest.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orcchg.yandexcontest.coremodel.Money
import com.orcchg.yandexcontest.coremodel.StockSelection
import com.orcchg.yandexcontest.coreui.AutoDisposeViewModel
import com.orcchg.yandexcontest.stocklist.api.StockListInteractor
import com.orcchg.yandexcontest.coremodel.Quote
import com.orcchg.yandexcontest.stocklist.api.model.Stock
import com.orcchg.yandexcontest.stocklist.convert.StockVoConverter
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.util.DataState
import com.uber.autodispose.autoDispose
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

internal class StockListViewModel @Inject constructor(
    private val interactor: StockListInteractor,
    private val stockSelection: StockSelection,
    private val stockVoConverter: StockVoConverter
) : AutoDisposeViewModel() {

    private var currentPageStockSelection: StockSelection = stockSelection

    private val _stocks = MutableLiveData<List<Stock>>() // actual stocks
    private val _stocksVo by lazy(LazyThreadSafetyMode.NONE) {
        val data = MutableLiveData<DataState<List<StockVO>>>()
        loadStocks(data)
        data
    }
    internal val stocks: LiveData<DataState<List<StockVO>>> by lazy(LazyThreadSafetyMode.NONE) { _stocksVo }

    init {
        interactor.favouriteIssuersChanged
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(this)
            .subscribe({ loadStocksFromLocalCache(_stocksVo) }, Timber::e)

        interactor.realTimeQuotes
            .doOnNext { Timber.v("Apply rt-quotes: ${it.joinToString { s -> "[${s.ticker}:${s.currentPrice}:${s.prevClosePrice}]" }}") }
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(this)
            .subscribe({ quotes ->
                val currentStocks = mutableListOf<Stock>()
                    .apply { _stocks.value?.let(this::addAll) }

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
                        _stocksVo.value = DataState.success(currentStocks.map(stockVoConverter::convert))
                    }
                } // stocks not empty
            }, Timber::e)
    }

    fun retryLoadStocks() {
        _stocksVo.value = DataState.success(emptyList())
        interactor.invalidateCache(stockSelection)
            .doOnComplete { loadStocks(_stocksVo) }
            .autoDispose(this)
            .subscribe({}, Timber::e)
    }

    fun setCurrentPage(stockSelection: StockSelection) {
        currentPageStockSelection = stockSelection
    }

    fun setIssuerFavourite(ticker: String, isFavourite: Boolean) {
        interactor.setIssuerFavourite(ticker, isFavourite)
            .autoDispose(this)
            .subscribe({}, Timber::e)
    }

    private fun loadStocks(data: MutableLiveData<DataState<List<StockVO>>>) {
        loadStocks(data, forceLocal = false, showLoading = true)
    }

    private fun loadStocksFromLocalCache(data: MutableLiveData<DataState<List<StockVO>>>) {
        loadStocks(data, forceLocal = true, showLoading = false)
    }

    private fun loadStocks(
        data: MutableLiveData<DataState<List<StockVO>>>,
        forceLocal: Boolean,
        showLoading: Boolean
    ) {
        val source = when (stockSelection) {
            StockSelection.ALL -> interactor.stocks(forceLocal)
            StockSelection.FAVOURITE -> interactor.favouriteStocks(forceLocal)
            else -> throw IllegalStateException("Unsupported stock selection")
        }
        source
            .doOnSubscribe { if (showLoading) data.value = DataState.loading() }
            .doOnSuccess { _stocks.value = it }
            .map(stockVoConverter::convertList)
            .autoDispose(this)
            .subscribe({
                data.value = DataState.success(it)
            }, {
                Timber.e(it)
                data.value = DataState.failure(it)
            })
    }
}
