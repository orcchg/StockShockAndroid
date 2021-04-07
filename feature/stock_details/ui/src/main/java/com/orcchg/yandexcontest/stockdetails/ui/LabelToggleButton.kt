package com.orcchg.yandexcontest.stockdetails.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.orcchg.yandexcontest.androidutil.themeAttribute

@Suppress("CheckResult")
class LabelToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CompoundButton(context, attrs, defStyleAttr) {

    init {
        val padding = context.resources.getDimensionPixelSize(R.dimen.keyline_4)
        val size = context.resources.getDimensionPixelSize(R.dimen.keyline_12)
        setPadding(padding, padding, padding, padding)
        background = ContextCompat.getDrawable(context, R.drawable.stock_details_label_btn_bg)
        minHeight = size
        minWidth = size
        isClickable = true
        setTextAppearance(context.themeAttribute(R.attr.textAppearanceBody2))
        setTextColor(ContextCompat.getColorStateList(context, R.color.stock_details_label_btn_text_color))
        context.obtainStyledAttributes(attrs, R.styleable.LabelToggleButton, defStyleAttr, 0)
            .use {
                val text = it.getString(R.styleable.LabelToggleButton_stock_details_toggle_btn_text)
                this.text = text
            }
        gravity = Gravity.CENTER
    }
}
