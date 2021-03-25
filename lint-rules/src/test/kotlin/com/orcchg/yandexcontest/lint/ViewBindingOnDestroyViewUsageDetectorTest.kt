package com.orcchg.yandexcontest.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.lint.rules.ViewBindingOnDestroyViewUsageDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ViewBindingOnDestroyViewUsageDetectorTest : LintDetectorTest() {

    override fun getDetector(): Detector {
        return ViewBindingOnDestroyViewUsageDetector()
    }

    override fun getIssues(): MutableList<Issue> {
        return mutableListOf(ViewBindingOnDestroyViewUsageDetector.ISSUE)
    }

    @Test
    fun expectFail() {
        lint()
            .files(
                FRAGMENT_STUB,
                VIEW_BINDING_STUB,
                ACTIVITY_MAIN_BINDING_STUB,
                VIEW_BINDABLE_FRAGMENT_STUB,
                kotlin(
                    """package ru.sberbank.sa.mobile.app

import androidx.fragment.app.Fragment
import ru.sberbank.sa.mobile.lib.core_ui.view.ViewBindableFragment
import ru.sberbank.sa.mobile.app.databinding.TestFragmentBinding

class TestFragment : Fragment(), ViewBindableFragment {

    private val binding = TestFragmentBinding.bind(requireView())

    override fun onDestroyView() {
        super.onDestroyView()
        binding.text.setText(null)
        binding.text.text = null
        with(binding) {
            text.text = null
        }
    }
}"""
                )
            )
            .run()
            .expect(
                """src/ru/sberbank/sa/mobile/app/TestFragment.kt:13: Error: Avoid referencing ViewBinding in onDestroyView. Use onDestroyBinding instead [ViewBindingOnDestroyViewUsageDetector]
        binding.text.setText(null)
        ~~~~~~~
src/ru/sberbank/sa/mobile/app/TestFragment.kt:14: Error: Avoid referencing ViewBinding in onDestroyView. Use onDestroyBinding instead [ViewBindingOnDestroyViewUsageDetector]
        binding.text.text = null
        ~~~~~~~
src/ru/sberbank/sa/mobile/app/TestFragment.kt:15: Error: Avoid referencing ViewBinding in onDestroyView. Use onDestroyBinding instead [ViewBindingOnDestroyViewUsageDetector]
        with(binding) {
             ~~~~~~~
3 errors, 0 warnings"""
            )
    }
}

val VIEW_BINDABLE_FRAGMENT_STUB = LintDetectorTest.kotlin(
    """package ru.sberbank.sa.mobile.lib.core_ui.view

interface ViewBindableFragment"""
)

val ACTIVITY_MAIN_BINDING_STUB = LintDetectorTest.kotlin(
    """
package ru.sberbank.sa.mobile.app.databinding;

import android.view.View
import android.widget.TextView
import androidx.viewbinding.ViewBinding;

class TestFragmentBinding : ViewBinding {

  val text: TextView

  companion object {
    fun bind(rootView: View): TestFragmentBinding {
      return TestFragmentBinding()
    }
  }
}
"""
)

val VIEW_BINDING_STUB = LintDetectorTest.kotlin(
    """package androidx.viewbinding;

import android.view.View

interface ViewBinding {
    fun root(): View
}"""
)

val FRAGMENT_STUB = LintDetectorTest.kotlin(
    """package androidx.fragment.app;

class Fragment {
    
    open fun onDestroyView() {
    
    }
}"""
)
