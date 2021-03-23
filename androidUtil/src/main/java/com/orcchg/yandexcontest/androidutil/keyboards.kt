package com.orcchg.yandexcontest.androidutil

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

/**
 * Focuses this [View] and shows soft keyboard.
 *
 * @see https://developer.squareup.com/blog/showing-the-android-keyboard-reliably/
 * @see https://stackoverflow.com/questions/1109022/how-do-you-close-hide-the-android-soft-keyboard-using-java
 */
fun View.showKeyboard(isShow: Boolean) {
    /**
     * This is to be called when the window already has focus.
     */
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (isShow) {
                    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                } else {
                    imm.hideSoftInputFromWindow(this.windowToken, 0)
                }
            }
        }
    }

    requestFocus()
    if (hasWindowFocus()) {
        // No need to wait for the window to get focus.
        showTheKeyboardNow()
    } else {
        // We need to wait until the window gets focus.
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    // This notification will arrive just before the InputMethodManager gets set up.
                    if (hasFocus) {
                        this@showKeyboard.showTheKeyboardNow()
                        // It’s very important to remove this listener once we are done.
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            })
    }
}

fun View.showKeyboard() = showKeyboard(true)
fun View.hideKeyboard() = showKeyboard(false)
