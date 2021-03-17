package com.orcchg.yandexcontest.stocklist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.orcchg.yandexcontest.stocklist.model.StockVO

class StockListDiffCallback : DiffUtil.ItemCallback<StockVO>() {

    override fun areItemsTheSame(oldItem: StockVO, newItem: StockVO): Boolean =
        oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: StockVO, newItem: StockVO): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: StockVO, newItem: StockVO): Any? {
        if (oldItem.isFavourite != newItem.isFavourite) {
            return ChangeIsFavourite
        }
        return super.getChangePayload(oldItem, newItem)
    }
}

object ChangeIsFavourite
