package com.orcchg.yandexcontest.stocklist.adapter

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orcchg.yandexcontest.androidutil.themeColor
import com.orcchg.yandexcontest.design.R
import com.orcchg.yandexcontest.design.view.TextBgPic
import com.orcchg.yandexcontest.stocklist.databinding.StockListItemBinding
import com.orcchg.yandexcontest.stocklist.model.StockVO

class StockViewHolder(
    private val binding: StockListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vo: StockVO) {
        fun placeholder(): Drawable? =
            if (vo.logoResId == 0 && vo.logoUrl.isNullOrBlank()) {
                TextBgPic(
                    char = vo.name[0],
                    foregroundColor = itemView.context.themeColor(R.attr.colorOnPrimary),
                    backgroundColor = itemView.context.themeColor(R.attr.colorPrimary),
                    typeFace = Typeface.DEFAULT_BOLD,
                    cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.keyline_3)
                        .toFloat()
                )
            } else null

        @ColorInt val priceChangeTextColor = when (vo.priceDailyChange[0]) {
            '+' -> ContextCompat.getColor(itemView.context, R.color.green)
            '-' -> ContextCompat.getColor(itemView.context, R.color.red)
            else -> ContextCompat.getColor(itemView.context, R.color.grey)
        }

        with(binding) {
            Glide.with(itemView)
                .load(vo.logoResId)
                .placeholder(placeholder())
                .into(binding.ivStockLogo)

            tvStockTicker.text = vo.ticker
            tvStockIssuer.text = vo.name
            tvStockPrice.text = vo.price
            tvStockPriceChange.text = vo.priceDailyChange
            tvStockPriceChange.setTextColor(priceChangeTextColor)
        }
    }
}
