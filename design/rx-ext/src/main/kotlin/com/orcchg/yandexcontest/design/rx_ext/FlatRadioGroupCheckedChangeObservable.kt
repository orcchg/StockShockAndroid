package com.orcchg.yandexcontest.design.rx_ext

import androidx.annotation.CheckResult
import com.jakewharton.rxbinding3.InitialValueObservable
import com.jakewharton.rxbinding3.internal.checkMainThread
import com.orcchg.yandexcontest.design.view.FlatRadioGroup
import com.orcchg.yandexcontest.design.view.FlatRadioGroup.Companion.NO_ID
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

@CheckResult
fun FlatRadioGroup.checkedChanges(): InitialValueObservable<Pair<Int, Boolean>> =
    FlatRadioGroupCheckedChangeObservable(this)

internal class FlatRadioGroupCheckedChangeObservable(
    private val view: FlatRadioGroup
) : InitialValueObservable<Pair<Int, Boolean>>() {

    override val initialValue: Pair<Int, Boolean>
        get() = view.currentCheckedViewId to (view.currentCheckedViewId != NO_ID)

    @Suppress("RestrictedApi")
    override fun subscribeListener(observer: Observer<in Pair<Int, Boolean>>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.onCheckedChangeListener = listener
    }

    private class Listener(
        private val view: FlatRadioGroup,
        private val observer: Observer<in Pair<Int, Boolean>>
    ) : MainThreadDisposable(), FlatRadioGroup.OnCheckedChangeListener {

        override fun onCheckedChanged(
            radioGroup: FlatRadioGroup,
            checkedViewId: Int,
            isChecked: Boolean
        ) {
            if (!isDisposed) {
                observer.onNext(checkedViewId to isChecked)
            }
        }

        override fun onDispose() {
            view.onCheckedChangeListener = null
        }
    }
}
