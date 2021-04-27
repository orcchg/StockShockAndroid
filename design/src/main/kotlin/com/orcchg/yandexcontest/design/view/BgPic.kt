package com.orcchg.yandexcontest.design.view

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import kotlin.math.min

open class BgPic(
    @ColorInt protected val foregroundColor: Int,
    @ColorInt protected val backgroundColor: Int,
    protected val cornerRadius: Float
) : Drawable() {

    protected val tmpRect = RectF()

    protected val backgroundPaint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = backgroundColor
    }

    @CallSuper
    override fun draw(canvas: Canvas) {
        val centerX = bounds.exactCenterX()
        val centerY = bounds.exactCenterY()
        val r = min(bounds.height(), bounds.width()) / 2F
        tmpRect.set(centerX - r, centerY - r, centerX + r, centerY + r)
        canvas.drawRoundRect(tmpRect, cornerRadius, cornerRadius, backgroundPaint)
    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT

    @CallSuper
    override fun setAlpha(alpha: Int) {
        backgroundPaint.alpha = alpha
    }

    @CallSuper
    override fun setColorFilter(colorFilter: ColorFilter?) {
        backgroundPaint.colorFilter = colorFilter
    }
}
