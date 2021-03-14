package com.orcchg.yandexcontest.design.decorator

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class HorizontalMarginDecoration(resources: Resources, @DimenRes resId: Int) :
    RecyclerView.ItemDecoration() {

    private val margin: Int = resources.getDimensionPixelSize(resId)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = margin
            right = margin
        }
    }
}
