package com.orcchg.yandexcontest.stocklist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.orcchg.yandexcontest.stocklist.databinding.StockListItemBinding
import com.orcchg.yandexcontest.stocklist.model.StockVO

class StockViewHolder(
    private val binding: StockListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vo: StockVO) {
        with(binding) {
            // TODO: bind image with global Glide
            tvStockTicker.text = vo.ticker
            tvStockIssuer.text = vo.name
            tvStockPrice.text = vo.price
            tvStockPriceChange.text = vo.priceDailyChange
        }
    }
}
