package com.orcchg.yandexcontest.coreui

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.orcchg.yandexcontest.coredi.ApiContainer

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    protected val api by lazy(LazyThreadSafetyMode.NONE) { application as ApiContainer }
}
