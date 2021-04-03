package com.orcchg.yandexcontest.staticanalysis.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.staticanalysis.lint.rules.VectorDrawableFillColorDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class VectorDrawableFillColorDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(VectorDrawableFillColorDetector.ISSUE)

    override fun getDetector(): Detector = VectorDrawableFillColorDetector()

    @Test
    fun `expect pass`() {
        lint()
            .files(
                xml(
                    "res/drawable/icon.xml", """
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:tint="?attr/colorOnBackgroundSecondary"
    android:viewportWidth="24.0"
    android:viewportHeight="24.0">
    <path
        android:fillColor="#000000"
        android:pathData="M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z" />
</vector>
"""
                )
            ).run()
            .expectClean()
    }

    @Test
    fun `alpha channel - expect fail`() {
        lint()
            .files(
                xml(
                    "res/drawable/icon.xml", """
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:tint="?attr/colorOnBackgroundSecondary"
    android:viewportWidth="24.0"
    android:viewportHeight="24.0">
    <path
        android:fillColor="#00000000"
        android:pathData="M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z" />
</vector>
"""
                )
            ).run()
            .expect(
                """res/drawable/icon.xml:9: Error: FillColor should be a solid color without opacity, preferably #000000.
For tinting use android:tint on a <vector> element or a view [VectorDrawableFillColorDetector]
        android:fillColor="#00000000"
                           ~~~~~~~~~
1 errors, 0 warnings"""
            )
    }

    @Test
    fun `theme attr - expect fail`() {
        lint()
            .files(
                xml(
                    "res/drawable/icon.xml", """
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:tint="?attr/colorOnBackgroundSecondary"
    android:viewportWidth="24.0"
    android:viewportHeight="24.0">
    <path
        android:fillColor="?attr/colorOnBackgroundSecondary"
        android:pathData="M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z" />
</vector>
"""
                )
            ).run()
            .expect(
                """res/drawable/icon.xml:9: Error: FillColor should be a solid color without opacity, preferably #000000.
For tinting use android:tint on a <vector> element or a view [VectorDrawableFillColorDetector]
        android:fillColor="?attr/colorOnBackgroundSecondary"
                           ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings"""
            )
    }
}