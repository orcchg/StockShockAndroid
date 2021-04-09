package com.orcchg.yandexcontest.androidutil

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

inline fun Lifecycle.doOnEvent(target: Lifecycle.Event, crossinline block: () -> Unit) {
    addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == target) {
                removeObserver(this)
                block.invoke()
            }
        }
    })
}

inline fun Lifecycle.observeEvent(target: Lifecycle.Event, crossinline block: () -> Unit): LifecycleEventObserver {
    val observer = LifecycleEventObserver { _, event -> if (event == target) block.invoke() }
    addObserver(observer)
    return observer
}
