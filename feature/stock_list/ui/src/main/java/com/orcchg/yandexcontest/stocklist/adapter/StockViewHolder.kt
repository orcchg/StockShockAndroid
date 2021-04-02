package com.orcchg.yandexcontest.stocklist.adapter

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orcchg.yandexcontest.androidutil.themeColor
import com.orcchg.yandexcontest.design.view.TextBgPic
import com.orcchg.yandexcontest.stocklist.R
import com.orcchg.yandexcontest.stocklist.databinding.StockListItemBinding
import com.orcchg.yandexcontest.stocklist.model.StockVO
import com.orcchg.yandexcontest.design.R as Design

class StockViewHolder(
    val binding: StockListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vo: StockVO) {
        fun placeholder(): Drawable? =
            if (vo.logoResId == 0 && vo.logoUrl.isNullOrBlank()) {
                TextBgPic(
                    char = vo.name[0],
                    foregroundColor = itemView.context.themeColor(Design.attr.colorOnPrimary),
                    backgroundColor = itemView.context.themeColor(Design.attr.colorPrimary),
                    typeFace = Typeface.DEFAULT_BOLD,
                    cornerRadius = itemView.resources.getDimensionPixelSize(Design.dimen.keyline_3)
                        .toFloat()
                )
            } else null

        with(binding) {
            if (vo.logoResId != 0) {
                Glide.with(itemView).load(vo.logoResId)
            } else {
                Glide.with(itemView).load(vo.logoUrl)
            }
                .placeholder(placeholder())
                .into(binding.ivStockLogo)

            tvStockTicker.text = vo.ticker
            tvStockIssuer.text = vo.name
        }

        setFavIcon(vo)
        setPrice(vo)
        setPriceChange(vo)
    }

    fun bind(vo: StockVO, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            bind(vo)
            return
        }

        if (payloads.isNotEmpty()) {
            payloads[0].takeIf { it is Set<*> }?.let { it as Set<*> }
                ?.let {
                    if (it.contains(ChangeIsFavourite)) {
                        setFavIcon(vo)
                    }
                    if (it.contains(ChangePrice)) {
                        setPrice(vo)
                    }
                    if (it.contains(ChangePriceDailyChange)) {
                        setPriceChange(vo)
                    }
                }
        }
    }

    private fun setFavIcon(vo: StockVO) {
        @DrawableRes val favIcon = if (vo.isFavourite) {
            R.drawable.stock_ic_fav
        } else {
            R.drawable.stock_ic_unfav
        }

        binding.ibtnFavourite.setImageResource(favIcon)
    }

    private fun setPrice(vo: StockVO) {
        binding.tvStockPrice.text = vo.price
    }

    private fun setPriceChange(vo: StockVO) {
        @ColorInt val priceChangeTextColor = when (vo.priceDailyChange[0]) {
            '+' -> ContextCompat.getColor(itemView.context, Design.color.green)
            '-' -> ContextCompat.getColor(itemView.context, Design.color.red)
            else -> ContextCompat.getColor(itemView.context, Design.color.grey)
        }

        with(binding) {
            tvStockPriceChange.text = vo.priceDailyChange
            tvStockPriceChange.setTextColor(priceChangeTextColor)
        }
    }
}
