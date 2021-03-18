package com.orcchg.yandexcontest.search_bar.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.androidutil.inputDebounce
import com.orcchg.yandexcontest.search_bar.ui.databinding.SearchBarLayoutBinding

@SuppressLint("CheckResult")
class SearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = SearchBarLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var focusedBg: Drawable? = null
    private var normalBg: Drawable? = null

    var onBackPressedListener: OnBackPressedListener? = null
    var onTextChangedListener: OnTextChangedListener? = null

    private var ignoreTextChange: Boolean = false

    init {
        // TODO: fix focused bg
        focusedBg = ResourcesCompat.getDrawable(context.resources, R.drawable.search_bar_focused_bg, context.theme)
        normalBg = ResourcesCompat.getDrawable(context.resources, R.drawable.search_bar_normal_bg, context.theme)
        background = normalBg
//        background = ResourcesCompat.getDrawable(context.resources, R.drawable.search_bar_bg, context.theme)
        isClickable = true
        isFocusable = true
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val h = context.resources.getDimensionPixelSize(R.dimen.keyline_8)
        maxHeight = h
        minHeight = h

        binding.ibtnSearchBack.clicks().clickThrottle().subscribe {
            clearInputSilent()
            setFocus(false)
            onBackPressedListener?.onBackPressed()
        }
        binding.ibtnSearchBarClear.clicks().clickThrottle().subscribe {
            clearInput()
        }
        binding.etSearchInput.textChanges().skipInitialValue().inputDebounce().subscribe { text ->
            binding.ibtnSearchBarClear.isInvisible = text.isNullOrBlank()
            if (!ignoreTextChange) {
                onTextChangedListener?.onTextChanged(text)
            }
        }
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        setFocus(gainFocus)
    }

    private fun clearInput() {
        binding.etSearchInput.text = null
    }

    private fun clearInputSilent() {
        ignoreTextChange = true
        clearInput()
        post { ignoreTextChange = false }
    }

    private fun setFocus(gainFocus: Boolean) {
        background = if (gainFocus) focusedBg else normalBg
        binding.ivSearchIcon.isInvisible = gainFocus
        binding.ibtnSearchBack.isInvisible = !gainFocus
        // TODO: hide/show keyboard
    }

    fun interface OnBackPressedListener {
        fun onBackPressed()
    }

    fun interface OnTextChangedListener {
        fun onTextChanged(text: CharSequence?)
    }
}