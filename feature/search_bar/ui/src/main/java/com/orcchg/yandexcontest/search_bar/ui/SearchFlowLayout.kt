package com.orcchg.yandexcontest.search_bar.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class SearchFlowLayout@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val xpos = maxOf(paddingLeft, paddingStart)
        val ypos = paddingTop
        val width: Int = MeasureSpec.getSize(widthMeasureSpec) - xpos - maxOf(paddingRight, paddingEnd)
        val height: Int = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        
    }
}
