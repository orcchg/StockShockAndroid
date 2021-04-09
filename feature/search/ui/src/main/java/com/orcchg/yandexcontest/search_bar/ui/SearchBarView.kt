package com.orcchg.yandexcontest.search_bar.ui

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
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

@Suppress("CheckResult", "Unused")
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

    private var ignoreFocusChange: Boolean = false
    private var ignoreTextChange: Boolean = false

    init {
        isSaveEnabled = true
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
        binding.etSearchInput.focusChanges()
            .skipInitialValue()
            .filter { !ignoreFocusChange }
            .inputDebounce()
            .subscribe(::setFocus)

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

    fun setText(text: CharSequence?) {
        binding.etSearchInput.setText(text)
        binding.etSearchInput.setSelection(text?.length ?: 0)
        setFocus(gainFocus = !text.isNullOrBlank())
    }

    fun getText(): CharSequence? = binding.etSearchInput.text

    private fun clearInput() {
        binding.etSearchInput.text = null
        binding.ibtnSearchBarClear.isInvisible = true
    }

    private fun clearInputSilent() {
        ignoreTextChange = true
        clearInput()
        post { ignoreTextChange = false }
    }

    private fun setFocus(gainFocus: Boolean): Boolean {
        background = if (gainFocus) focusedBg else normalBg
        if (!gainFocus) {
            ignoreFocusChange = true
            binding.etSearchInput.hideKeyboard()
            if (binding.etSearchInput.hasFocus()) {
                binding.etSearchInput.clearFocus()
            }
            post { ignoreFocusChange = false }
        }
        binding.ivSearchIcon.isInvisible = gainFocus
        binding.ibtnSearchBack.isInvisible = !gainFocus
        if (gainFocus) {
            onFocusGainListener?.onFocused()
        }
        return gainFocus
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

    /**
     * Save View state.
     *
     * https://kirillsuslov.medium.com/how-to-save-android-view-state-in-kotlin-9dbe96074d49
     * https://medium.com/super-declarative/android-how-to-save-state-in-a-custom-view-30e5792c584b
     */
    private class SavedState : BaseSavedState {
        var hasFocus: Boolean = false

        constructor(parcel: Parcel) : super(parcel) {
            hasFocus = parcel.readInt() != 0
        }

        constructor(parcelable: Parcelable?) : super(parcelable)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(if (hasFocus) 1 else 0)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return SavedState(superState).apply {
            this.hasFocus = binding.etSearchInput.hasFocus()
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            ignoreFocusChange = true
            if (setFocus(state.hasFocus)) {
                binding.etSearchInput.requestFocus()
            }
            post { ignoreFocusChange = false }
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}
