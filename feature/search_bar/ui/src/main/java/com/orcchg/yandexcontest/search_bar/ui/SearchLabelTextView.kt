package com.orcchg.yandexcontest.search_bar.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.orcchg.yandexcontest.androidutil.themeAttribute

class SearchLabelTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    init {
        val sidePadding = context.resources.getDimensionPixelSize(R.dimen.keyline_4)
        val vertPadding = context.resources.getDimensionPixelSize(R.dimen.keyline_3)
        background = ResourcesCompat.getDrawable(context.resources, R.drawable.search_label_tv_bg, context.theme)
        setPadding(sidePadding, vertPadding, sidePadding, vertPadding)
        setTextAppearance(context.themeAttribute(R.attr.textAppearanceBody2))
    }
}
