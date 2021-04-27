package com.orcchg.yandexcontest.androidutil

import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

@MainThread
inline fun <reified VM : ViewModel> Fragment.sharedViewModels(
    @IdRes navGraphId: Int,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) =
    viewModels<VM>(
        ownerProducer = { findNavController().getViewModelStoreOwner(navGraphId) },
        factoryProducer = factoryProducer
    )

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) =
    viewModels<VM>(
        ownerProducer = { requireParentFragment() },
        factoryProducer = factoryProducer
    )
