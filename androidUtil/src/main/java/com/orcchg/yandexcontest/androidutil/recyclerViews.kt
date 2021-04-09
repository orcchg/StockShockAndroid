package com.orcchg.yandexcontest.androidutil

import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

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

var ViewPager2.detachableAdapter: RecyclerView.Adapter<*>?
    get() = adapter
    set(value) {
        adapter = value

        findViewTreeLifecycleOwner()?.lifecycle?.doOnEvent(ON_DESTROY, ::detachAdapter)
            ?: doOnFirstDetach { detachAdapter() }
    }

fun ViewPager2.detachAdapter() {
    adapter = null
}
