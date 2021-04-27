package com.orcchg.yandexcontest.coreui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.orcchg.yandexcontest.coredi.ApiContainer

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected val api by lazy(LazyThreadSafetyMode.NONE) { requireActivity().application as ApiContainer }
}
