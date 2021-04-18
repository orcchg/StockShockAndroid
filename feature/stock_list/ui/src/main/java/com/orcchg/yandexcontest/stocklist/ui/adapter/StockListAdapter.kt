package com.orcchg.yandexcontest.stocklist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.stocklist.ui.databinding.StockListItemBinding
import com.orcchg.yandexcontest.stocklist.ui.model.StockVO
import javax.inject.Inject

class StockListAdapter @Inject constructor(
    diffUtil: StockListDiffCallback
) : ListAdapter<StockVO, StockViewHolder>(diffUtil) {

    var itemClickListener: ((model: StockVO) -> Unit)? = null
    var favIconClickListener: ((model: StockVO) -> Unit)? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder =
        StockListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let(::StockViewHolder).apply {
                itemView.clicks().clickThrottle().subscribe {
                    adapterPosition
                        .takeIf { it != RecyclerView.NO_POSITION }
                        ?.let { itemClickListener?.invoke(getItem(it)) }
                }

                binding.ibtnFavourite.clicks().clickThrottle().subscribe {
                    adapterPosition
                        .takeIf { it != RecyclerView.NO_POSITION }
                        ?.let { pos ->
                            val oldItem = getItem(pos)
                            val newItem = oldItem.copy(isFavourite = !oldItem.isFavourite)
                            val newList = currentList.toMutableList().apply { set(pos, newItem) }
                            submitList(newList) { favIconClickListener?.invoke(newItem) }
                        }
                }
            }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.bind(getItem(position), payloads)
    }

    override fun getItemId(position: Int): Long = getItem(position).id()

    fun update(items: List<StockVO>) {
        submitList(items)
    }

    fun update(
        predicate: (StockVO) -> Boolean,
        updateItem: (StockVO) -> StockVO,
        updateList: ((StockVO) -> Unit)? = null
    ) {
        currentList.indexOfFirst(predicate)
            .takeIf { it != -1 }
            ?.let { pos ->
                val oldItem = getItem(pos)
                val newItem = updateItem(oldItem)
                val newList = currentList.toMutableList().apply { set(pos, newItem) }
                submitList(newList) { updateList?.invoke(newItem) }
            }
    }
}
