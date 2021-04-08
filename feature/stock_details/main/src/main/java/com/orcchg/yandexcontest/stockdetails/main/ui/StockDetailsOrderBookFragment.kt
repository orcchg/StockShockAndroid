package com.orcchg.yandexcontest.stockdetails.main.ui

import com.orcchg.yandexcontest.coreui.BaseFragment
import com.orcchg.yandexcontest.stockdetails.main.R

internal class StockDetailsOrderBookFragment : BaseFragment(R.layout.stock_details_orderbook_fragment) {

    companion object {
        @JvmStatic
        fun newInstance(): StockDetailsOrderBookFragment = StockDetailsOrderBookFragment()
    }
}
