package com.orcchg.yandexcontest.search_bar.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use

class SearchFlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var horizontalSpacing = 0
    private var verticalSpacing = 0
    private var endSideLedge = 0

    init {
        val defaultHorizontalSpacing = context.resources.getDimensionPixelSize(R.dimen.keyline_1)
        val defaultVerticalSpacing = context.resources.getDimensionPixelSize(R.dimen.keyline_2)

        context.obtainStyledAttributes(attrs, R.styleable.SearchFlowLayout, defStyleAttr, 0).use {
            horizontalSpacing = it.getDimensionPixelSize(R.styleable.SearchFlowLayout_sflHorizontalSpacing, defaultHorizontalSpacing)
            verticalSpacing = it.getDimensionPixelSize(R.styleable.SearchFlowLayout_sflVerticalSpacing, defaultVerticalSpacing)
            endSideLedge = it.getDimensionPixelSize(R.styleable.SearchFlowLayout_sflEndSideLedge, 0)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val startPadding = maxOf(paddingLeft, paddingStart)
        var xpos = startPadding
        var ypos = paddingTop
        val width = r - l
        val availableWidth = width + endSideLedge
        var row = 0
        var maxChildHeight = 0

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == View.GONE) {
                continue
            }

            maxChildHeight = maxOf(maxChildHeight, childView.measuredHeight + verticalSpacing)

            if (xpos + childView.measuredWidth > availableWidth) { // new line
                xpos = startPadding
                ypos += maxChildHeight
                ++row
            }

            childView.layout(xpos, ypos, xpos + childView.measuredWidth, ypos + childView.measuredHeight)
            xpos += childView.measuredWidth + horizontalSpacing
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val startPadding = maxOf(paddingLeft, paddingStart)
        var xpos = startPadding
        var ypos = paddingTop
        val width: Int = MeasureSpec.getSize(widthMeasureSpec) - xpos - maxOf(paddingRight, paddingEnd)
        val availableWidth = width + endSideLedge
        var height: Int = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val childViewWidthSpec = MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.AT_MOST)
        val childViewHeightSpec =
            when (MeasureSpec.getMode(heightMeasureSpec)) {
                MeasureSpec.AT_MOST -> MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
                else -> MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            }
//        val childViewHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)

        var row = 0
        var maxChildHeight = 0

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == View.GONE) {
                continue
            }

            childView.measure(childViewWidthSpec, childViewHeightSpec)
            maxChildHeight = maxOf(maxChildHeight, childView.measuredHeight + verticalSpacing)

            if (xpos + childView.measuredWidth > availableWidth) { // new line
                xpos = startPadding
                ypos += maxChildHeight
                ++row
            }

            xpos += childView.measuredWidth + horizontalSpacing
        }

        when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.AT_MOST -> if (height > ypos + maxChildHeight) {
                height = ypos + maxChildHeight
            }
            MeasureSpec.UNSPECIFIED -> height = ypos + maxChildHeight
            else -> { /* no-op */ }
        }

        setMeasuredDimension(width, height)
    }
}
