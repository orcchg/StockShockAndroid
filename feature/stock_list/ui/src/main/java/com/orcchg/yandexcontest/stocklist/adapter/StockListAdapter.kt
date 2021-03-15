package com.orcchg.yandexcontest.stocklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.stocklist.databinding.StockListItemBinding
import com.orcchg.yandexcontest.stocklist.model.StockVO
import javax.inject.Inject

class StockListAdapter @Inject constructor() : ListAdapter<StockVO, StockViewHolder>(StockListDiffCallback()) {

    var itemClickListener: ((model: StockVO) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder =
        StockListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::StockViewHolder).apply {
                this@apply.itemView.clicks().clickThrottle().subscribe {
                    adapterPosition
                        .takeIf { it != RecyclerView.NO_POSITION }
                        ?.let { itemClickListener?.invoke(getItem(it)) }
                }
            }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // TODO: use good hash code
    override fun getItemId(position: Int): Long = getItem(position).id()

    fun update(items: List<StockVO>) {
        submitList(items)
    }
}
