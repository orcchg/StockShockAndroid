package com.orcchg.yandexcontest.stocklist.convert

import com.orcchg.yandexcontest.util.ResourceSupplier
import javax.inject.Inject

class RealLogoResByTickerSupplier @Inject constructor() : ResourceSupplier {

    // operation not supported for real data
    override fun getResIdByKey(key: String): Int = 0
}
