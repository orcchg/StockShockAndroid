package com.orcchg.yandexcontest.stocklist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.orcchg.yandexcontest.stocklist.model.StockVO

class StockListDiffCallback : DiffUtil.ItemCallback<StockVO>() {

    override fun areItemsTheSame(oldItem: StockVO, newItem: StockVO): Boolean =
        oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: StockVO, newItem: StockVO): Boolean =
        oldItem == newItem
}
