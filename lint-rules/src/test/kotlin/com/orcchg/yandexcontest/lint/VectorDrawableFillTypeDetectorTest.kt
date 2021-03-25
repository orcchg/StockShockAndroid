package com.orcchg.yandexcontest.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.lint.rules.VectorDrawableFillTypeDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class VectorDrawableFillTypeDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(VectorDrawableFillTypeDetector.ISSUE)

    override fun getDetector(): Detector = VectorDrawableFillTypeDetector()

    @Test
    fun `vector expect pass`() {
        lint()
            .files(
                xml(
                    "res/drawable-v24/icon.xml", """
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
  <path
      android:pathData="M12,21.5921C6.477,21.5921 2,17.2065 2,11.7962C2,6.193 6.803,1.6878 12.596,2.017C17.254,2.2815 21.095,5.8432 21.875,10.3494C22.453,13.6966 20.788,15.2708 21.723,20.3118C21.825,20.8614 21.302,21.309 20.756,21.1494C16.919,20.0228 14.501,21.5921 12,21.5921ZM12,13.2656C12.8284,13.2656 13.5,12.6077 13.5,11.7962C13.5,10.9847 12.8284,10.3268 12,10.3268C11.1716,10.3268 10.5,10.9847 10.5,11.7962C10.5,12.6077 11.1716,13.2656 12,13.2656ZM7,13.2656C7.8284,13.2656 8.5,12.6077 8.5,11.7962C8.5,10.9847 7.8284,10.3268 7,10.3268C6.1716,10.3268 5.5,10.9847 5.5,11.7962C5.5,12.6077 6.1716,13.2656 7,13.2656ZM17,13.2656C17.8284,13.2656 18.5,12.6077 18.5,11.7962C18.5,10.9847 17.8284,10.3268 17,10.3268C16.1716,10.3268 15.5,10.9847 15.5,11.7962C15.5,12.6077 16.1716,13.2656 17,13.2656Z"
      android:fillColor="?attr/colorOnSurface"
      android:fillType="evenOdd"/>
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
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
  <path
      android:pathData="M12,21.5921C6.477,21.5921 2,17.2065 2,11.7962C2,6.193 6.803,1.6878 12.596,2.017C17.254,2.2815 21.095,5.8432 21.875,10.3494C22.453,13.6966 20.788,15.2708 21.723,20.3118C21.825,20.8614 21.302,21.309 20.756,21.1494C16.919,20.0228 14.501,21.5921 12,21.5921ZM12,13.2656C12.8284,13.2656 13.5,12.6077 13.5,11.7962C13.5,10.9847 12.8284,10.3268 12,10.3268C11.1716,10.3268 10.5,10.9847 10.5,11.7962C10.5,12.6077 11.1716,13.2656 12,13.2656ZM7,13.2656C7.8284,13.2656 8.5,12.6077 8.5,11.7962C8.5,10.9847 7.8284,10.3268 7,10.3268C6.1716,10.3268 5.5,10.9847 5.5,11.7962C5.5,12.6077 6.1716,13.2656 7,13.2656ZM17,13.2656C17.8284,13.2656 18.5,12.6077 18.5,11.7962C18.5,10.9847 17.8284,10.3268 17,10.3268C16.1716,10.3268 15.5,10.9847 15.5,11.7962C15.5,12.6077 16.1716,13.2656 17,13.2656Z"
      android:fillColor="?attr/colorOnSurface"
      android:fillType="evenOdd"/>
</vector>
"""
                )
            ).run()
            .expect(
                """res/drawable/icon.xml:10: Error: fillType shouldn't be used in VectorDrawables on api less than 24 [VectorDrawableFillTypeDetector]
      android:fillType="evenOdd"/>
                        ~~~~~~~
1 errors, 0 warnings"""
            )
    }
}