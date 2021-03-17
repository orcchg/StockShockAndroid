package com.orcchg.yandexcontest.search_bar.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.orcchg.yandexcontest.search_bar.ui.databinding.SearchBarLayoutBinding

class SearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = SearchBarLayoutBinding.inflate(LayoutInflater.from(context), this)

    init {
        isClickable = true
        isFocusable = true
        setBackgroundResource(R.drawable.search_bar_bg)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val h = context.resources.getDimensionPixelSize(R.dimen.keyline_8)
        maxHeight = h
        minHeight = h
    }
}
