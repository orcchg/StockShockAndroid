package com.orcchg.yandexcontest.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.lint.rules.VectorDrawableSizeDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class VectorDrawableSizeDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(VectorDrawableSizeDetector.ISSUE)

    override fun getDetector(): Detector = VectorDrawableSizeDetector()

    @Test
    fun `vector expect pass`() {
        lint()
            .files(
                xml(
                    "res/drawable/icon.xml", """
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
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
    fun `shape expect pass`() {
        lint()
            .files(
                xml(
                    "res/drawable/icon.xml", """
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="?attr/colorPrimary" />
    <size android:width="2dp" android:height="2dp"/>
</shape>"""
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
                """res/drawable/icon.xml:3: Error: VectorDrawable size should be more than or equal to 24dp [VectorDrawableSizeDetector]
    android:width="12dp"
                   ~~~~
res/drawable/icon.xml:4: Error: VectorDrawable size should be more than or equal to 24dp [VectorDrawableSizeDetector]
    android:height="12dp"
                    ~~~~
res/drawable/icon.xml:5: Error: VectorDrawable size should be more than or equal to 24dp [VectorDrawableSizeDetector]
    android:viewportWidth="12"
                           ~~
res/drawable/icon.xml:6: Error: VectorDrawable size should be more than or equal to 24dp [VectorDrawableSizeDetector]
    android:viewportHeight="12"
                            ~~
4 errors, 0 warnings"""
            )
    }
}