package com.orcchg.yandexcontest.stocklist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.orcchg.yandexcontest.stocklist.model.StockVO

class StockListDiffCallback : DiffUtil.ItemCallback<StockVO>() {

    override fun areItemsTheSame(oldItem: StockVO, newItem: StockVO): Boolean =
        oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: StockVO, newItem: StockVO): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: StockVO, newItem: StockVO): Any? {
        val payloads = mutableSetOf<Any>()
        if (oldItem.isFavourite != newItem.isFavourite) {
            payloads.add(ChangeIsFavourite)
        }
        if (oldItem.price != newItem.price) {
            payloads.add(ChangePrice)
        }
        if (oldItem.priceDailyChange != newItem.priceDailyChange) {
            payloads.add(ChangePriceDailyChange)
        }
        return payloads
    }
}

object ChangeIsFavourite
object ChangePrice
object ChangePriceDailyChange
