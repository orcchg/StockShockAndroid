package com.orcchg.yandexcontest.design

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt

data class ViewObjectColor(
    @ColorInt val colors: IntArray,
    val orientation: GradientDrawable.Orientation
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ViewObjectColor

        if (!colors.contentEquals(other.colors)) return false
        if (orientation != other.orientation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = colors.contentHashCode()
        result = 31 * result + orientation.hashCode()
        return result
    }
}

val DefaultViewObjectColor =
    ViewObjectColor(
        colors = intArrayOf(
            Color.parseColor("#E6F3FF"),
            Color.parseColor("#F8E8FF")
        ),
        orientation = GradientDrawable.Orientation.LEFT_RIGHT
    )
