package com.orcchg.yandexcontest.staticanalysis.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.staticanalysis.lint.rules.PlatformStylesDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PlatformStylesDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(PlatformStylesDetector.ISSUE)

    override fun getDetector(): Detector = PlatformStylesDetector()

    @Test
    fun `expect pass`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<View xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="@color/black"
    style="@style/Widget.SuperApp.TextInputLayout"
    />
"""
                )
            ).run()
            .expectClean()
    }

    @Test
    fun `expect fail`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<View xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:background="#000"
    style="@style/Widget.Design.TextInputLayout"
    />
"""
                )
            ).run()
            .expect(
                """
res/layout/layout.xml:6: Error: Use .SuperApp. styles [PlatformStylesDetector]
    style="@style/Widget.Design.TextInputLayout"
           ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings
            """
            )
    }
}