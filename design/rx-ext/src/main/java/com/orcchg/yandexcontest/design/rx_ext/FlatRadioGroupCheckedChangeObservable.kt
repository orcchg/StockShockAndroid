package com.orcchg.yandexcontest.design.rx_ext

import androidx.annotation.CheckResult
import androidx.annotation.IdRes
import com.jakewharton.rxbinding3.InitialValueObservable
import com.jakewharton.rxbinding3.internal.checkMainThread
import com.orcchg.yandexcontest.design.view.FlatRadioGroup
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

@CheckResult
fun FlatRadioGroup.checkedChanges() : InitialValueObservable<Int> =
    FlatRadioGroupCheckedChangeObservable(this)

internal class FlatRadioGroupCheckedChangeObservable(
    private val view: FlatRadioGroup
) : InitialValueObservable<@IdRes Int>() {

    override val initialValue: Int
        get() = view.currentCheckedViewId

    override fun subscribeListener(observer: Observer<in Int>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.onCheckedChangeListener = listener
    }

    private class Listener(
        private val view: FlatRadioGroup,
        private val observer: Observer<in Int>
    ) : MainThreadDisposable(), FlatRadioGroup.OnCheckedChangeListener {

        override fun onCheckedChanged(radioGroup: FlatRadioGroup, checkedViewId: Int) {
            if (!isDisposed) {
                observer.onNext(checkedViewId)
            }
        }

        override fun onDispose() {
            view.onCheckedChangeListener = null
        }
    }
}
