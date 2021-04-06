package com.orcchg.yandexcontest.stockdetails.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.orcchg.yandexcontest.androidutil.themeAttribute

@Suppress("CheckResult")
class LabelToggleButton  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatToggleButton(context, attrs, defStyleAttr) {

    init {
        val padding = context.resources.getDimensionPixelSize(R.dimen.keyline_4)
        setPadding(padding, padding, padding, padding)
        background = ContextCompat.getDrawable(context, R.drawable.stock_details_label_btn_bg)
        setTextAppearance(context.themeAttribute(R.attr.textAppearanceBody2))
        setTextColor(ContextCompat.getColor(context, R.color.stock_details_label_btn_text_color))
        context.obtainStyledAttributes(attrs, R.styleable.LabelToggleButton, defStyleAttr, 0)
            .use {
                val text = it.getString(R.styleable.LabelToggleButton_stock_details_toggle_btn_text)
                textOff = text
                textOn = text
            }
    }
}
