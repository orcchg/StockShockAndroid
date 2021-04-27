package com.orcchg.yandexcontest.design.view

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import androidx.annotation.ColorInt

class TextBgPic(
    private val char: Char,
    @ColorInt foregroundColor: Int,
    @ColorInt backgroundColor: Int,
    private val typeFace: Typeface,
    cornerRadius: Float
) : BgPic(
    foregroundColor = foregroundColor,
    backgroundColor = backgroundColor,
    cornerRadius
) {

    private val textPaint: TextPaint = TextPaint().apply {
        isAntiAlias = true
        color = foregroundColor
        style = Paint.Style.FILL_AND_STROKE
        hinting = Paint.HINTING_ON
        typeface = this@TextBgPic.typeFace
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val centerX = bounds.exactCenterX()
        val centerY = bounds.exactCenterY()

        textPaint.textSize = 1F
        textPaint.textSize = .5F * tmpRect.width() * textPaint.measureText(char.toString())

        val width = textPaint.measureText(char.toString())
        canvas.drawText(
            char.toString(),
            centerX - width / 2F,
            centerY + textPaint.textSize / 2F + (textPaint.descent() + textPaint.ascent()) / 4F,
            textPaint
        )
    }

    override fun setAlpha(alpha: Int) {
        super.setAlpha(alpha)
        textPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        super.setColorFilter(colorFilter)
        textPaint.colorFilter = colorFilter
    }
}
