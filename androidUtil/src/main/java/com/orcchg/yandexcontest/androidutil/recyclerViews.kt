package com.orcchg.yandexcontest.androidutil

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView

var RecyclerView.detachableAdapter: RecyclerView.Adapter<*>?
    get() = adapter
    set(value) {
        adapter = value

        findViewTreeLifecycleOwner()?.lifecycle?.doOnEvent(ON_DESTROY, ::detachAdapter)
            ?: doOnFirstDetach { detachAdapter() }
    }

fun RecyclerView.detachAdapter() {
    adapter = null
}
