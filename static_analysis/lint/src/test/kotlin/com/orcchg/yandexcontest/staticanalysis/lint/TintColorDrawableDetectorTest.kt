package com.orcchg.yandexcontest.staticanalysis.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.staticanalysis.lint.rules.TintColorDrawableDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TintColorDrawableDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(TintColorDrawableDetector.ISSUE)

    override fun getDetector(): Detector = TintColorDrawableDetector()

    @Test
    fun `expect pass`() {
        lint()
            .files(
                xml(
                    "res/drawable/icon.xml", """
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="12dp"
    android:height="12dp"
    android:viewportWidth="12"
    android:viewportHeight="12"
    android:tint="?attr/colorOnSurface">
  <path
      android:pathData="M10.6326,0.4925L0.8459,4.5859C0.2926,4.8192 0.3059,5.6059 0.8592,5.8192L4.4192,7.1992C4.5926,7.2659 4.7326,7.4059 4.7992,7.5792L6.1726,11.1325C6.3859,11.6925 7.1792,11.7059 7.4126,11.1525L11.5126,1.3725C11.7326,0.8192 11.1792,0.2659 10.6326,0.4925Z"
      android:fillColor="#000000" />
</vector>
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
                    "res/drawable/icon.xml", """
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="12dp"
    android:height="12dp"
    android:viewportWidth="12"
    android:viewportHeight="12"
    android:tint="@color/white">
  <path
      android:pathData="M10.6326,0.4925L0.8459,4.5859C0.2926,4.8192 0.3059,5.6059 0.8592,5.8192L4.4192,7.1992C4.5926,7.2659 4.7326,7.4059 4.7992,7.5792L6.1726,11.1325C6.3859,11.6925 7.1792,11.7059 7.4126,11.1525L11.5126,1.3725C11.7326,0.8192 11.1792,0.2659 10.6326,0.4925Z"
      android:fillColor="#000000" />
</vector>
"""
                )
            ).run()
            .expect(
                """res/drawable/icon.xml:7: Error: Colors should be referenced as theme attributes [TintColorDrawableDetector]
    android:tint="@color/white">
                  ~~~~~~~~~~~~
1 errors, 0 warnings"""
            )
    }
}