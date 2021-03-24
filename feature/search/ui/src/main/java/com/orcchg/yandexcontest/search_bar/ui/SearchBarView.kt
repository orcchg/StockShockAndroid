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
import com.jakewharton.rxbinding3.view.focusChanges
import com.jakewharton.rxbinding3.widget.textChanges
import com.orcchg.yandexcontest.androidutil.clickThrottle
import com.orcchg.yandexcontest.androidutil.hideKeyboard
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
    var onFocusGainListener: OnFocusGainListener? = null
    var onTextChangedListener: OnTextChangedListener? = null

    private var ignoreTextChange: Boolean = false

    init {
        focusedBg = ResourcesCompat.getDrawable(context.resources, R.drawable.search_bar_focused_bg, context.theme)
        normalBg = ResourcesCompat.getDrawable(context.resources, R.drawable.search_bar_normal_bg, context.theme)
        background = normalBg
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
        binding.etSearchInput.focusChanges().skipInitialValue().inputDebounce().subscribe(::setFocus)
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
        binding.ibtnSearchBarClear.isInvisible = true
    }

    private fun clearInputSilent() {
        ignoreTextChange = true
        clearInput()
        post { ignoreTextChange = false }
    }

    private fun setFocus(gainFocus: Boolean) {
        background = if (gainFocus) focusedBg else normalBg
        if (!gainFocus && binding.etSearchInput.hasFocus()) {
            binding.etSearchInput.hideKeyboard()
            binding.etSearchInput.clearFocus()
        }
        binding.ivSearchIcon.isInvisible = gainFocus
        binding.ibtnSearchBack.isInvisible = !gainFocus
        if (gainFocus) {
            onFocusGainListener?.onFocused()
        }
    }

    fun interface OnBackPressedListener {
        fun onBackPressed()
    }

    fun interface OnFocusGainListener {
        fun onFocused()
    }

    fun interface OnTextChangedListener {
        fun onTextChanged(text: CharSequence?)
    }
}
