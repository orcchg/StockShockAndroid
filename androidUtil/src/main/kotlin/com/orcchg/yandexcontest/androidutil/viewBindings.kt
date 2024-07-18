package com.orcchg.yandexcontest.androidutil

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> Fragment.viewBindings(crossinline bind: (View) -> VB): Lazy<VB> =
    object : Lazy<VB> {
        private var binding: VB? = null

        override fun isInitialized() = binding != null

        override val value: VB
            get() = binding ?: bind.invoke(requireView()).also {
                binding = it
                // viewLifecycleOwnerLiveData set value after onCreateView
                viewLifecycleOwnerLiveData.observe(
                    this@viewBindings,
                    object : Observer<LifecycleOwner> {
                        override fun onChanged(value: LifecycleOwner) {
                            // onChanged called when STARTED
                            viewLifecycleOwnerLiveData.removeObserver(this)
                        }
                    }
                )
            }
    }

inline fun <reified VB : ViewBinding> FragmentActivity.viewBindings(
    crossinline inflate: (LayoutInflater) -> VB,
    setContentView: Boolean = true
): Lazy<VB> =
    object : Lazy<VB> {
        private var binding: VB? = null

        override fun isInitialized() = binding != null

        override val value: VB
            get() = binding ?: inflate.invoke(layoutInflater)
                .also {
                    if (setContentView) {
                        setContentView(it.root)
                    }
                    binding = it
                }
    }
