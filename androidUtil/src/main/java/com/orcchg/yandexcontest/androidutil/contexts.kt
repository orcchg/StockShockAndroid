package com.orcchg.yandexcontest.androidutil

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.AttrRes

fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}

fun Context.themeAttribute(@AttrRes attrRes: Int): Int = themeColor(attrRes)

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)
