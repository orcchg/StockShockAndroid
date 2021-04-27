package com.orcchg.yandexcontest.design

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

fun Context.attributeValue(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

fun Context.createDrawable(productColor: ViewObjectColor): Drawable {
    val rippleColor = ColorStateList.valueOf(attributeValue(android.R.attr.colorControlHighlight))
    val radius = resources.getDimension(R.dimen.keyline_4)
    val model = ShapeAppearanceModel.builder()
        .setAllCornerSizes(radius)
        .build()
    val gradient = GradientDrawable(productColor.orientation, productColor.colors).apply {
        cornerRadius = radius
    }
    return RippleDrawable(rippleColor, gradient, MaterialShapeDrawable(model))
}
