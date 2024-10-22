package com.orcchg.yandexcontest.search_bar.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.R as Material
import com.orcchg.yandexcontest.androidutil.themeAttribute
import com.orcchg.yandexcontest.design.R as Design

class SearchLabelTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val sidePadding = context.resources.getDimensionPixelSize(Design.dimen.keyline_4)
        val vertPadding = context.resources.getDimensionPixelSize(Design.dimen.keyline_3)
        background = ResourcesCompat.getDrawable(context.resources, R.drawable.search_label_tv_bg, context.theme)
        setPadding(sidePadding, vertPadding, sidePadding, vertPadding)
        setTextAppearance(context.themeAttribute(Material.attr.textAppearanceBody2))
        gravity = Gravity.CENTER
    }
}
