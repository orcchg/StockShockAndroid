package com.orcchg.yandexcontest.stocklist.adapter

import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orcchg.yandexcontest.design.R
import com.orcchg.yandexcontest.stocklist.databinding.StockListItemBinding
import com.orcchg.yandexcontest.stocklist.model.StockVO

class StockViewHolder(
    private val binding: StockListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vo: StockVO) {
        @ColorInt val priceChangeTextColor = when (vo.priceDailyChange[0]) {
            '+' -> ContextCompat.getColor(itemView.context, R.color.green)
            '-' -> ContextCompat.getColor(itemView.context, R.color.red)
            else -> ContextCompat.getColor(itemView.context, R.color.grey)
        }

        with(binding) {
            // TODO: bind image with global Glide
            // TODO: image placeholder
            Glide.with(itemView)
                .load(vo.logoResId)
                .into(binding.ivStockLogo)

            tvStockTicker.text = vo.ticker
            tvStockIssuer.text = vo.name
            tvStockPrice.text = vo.price
            tvStockPriceChange.text = vo.priceDailyChange
            tvStockPriceChange.setTextColor(priceChangeTextColor)
        }
    }
}
