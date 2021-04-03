package com.orcchg.yandexcontest.staticanalysis.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.staticanalysis.lint.rules.HardcodedHexColorXmlDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HardcodedHexColorXmlDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(HardcodedHexColorXmlDetector.ISSUE)

    override fun getDetector(): Detector = HardcodedHexColorXmlDetector()

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
    />
"""
                )
            ).run()
            .expect(
                """
res/layout/layout.xml:5: Error: Hardcoded hex colors should be declared in a <color> resource. [HardcodedHexColorXml]
    android:background="#000"
                        ~~~~
1 errors, 0 warnings
            """
            )
    }
}