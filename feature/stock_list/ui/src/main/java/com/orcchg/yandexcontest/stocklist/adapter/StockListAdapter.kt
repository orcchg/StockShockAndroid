package com.orcchg.yandexcontest.stocklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.orcchg.yandexcontest.stocklist.databinding.StockListItemBinding
import com.orcchg.yandexcontest.stocklist.model.StockVO

class StockListAdapter : ListAdapter<StockVO, StockViewHolder>(StockListDiffCallback()) {

    private val items = mutableListOf<StockVO>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder =
        StockListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::StockViewHolder)

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // TODO: use good hash code
    override fun getItemId(position: Int): Long = items[position].id()

    override fun getItemCount(): Int = items.size
}
