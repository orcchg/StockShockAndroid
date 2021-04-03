package com.orcchg.yandexcontest.staticanalysis.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.staticanalysis.lint.rules.HardcodedTextStyleSizeDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HardcodedTextStyleSizeDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(HardcodedTextStyleSizeDetector.ISSUE)

    override fun getDetector(): Detector = HardcodedTextStyleSizeDetector()

    @Test
    fun `expect pass`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/name_market"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginEnd="@dimen/keyline_3"
    android:layout_marginBottom="@dimen/keyline_0"
    android:ellipsize="end"
    android:gravity="left"
    android:textAppearance="?attr/textAppearanceBody1"
    android:textColor="?colorPrimary"
    app:layout_constraintBottom_toTopOf="@id/delivery_time"
    app:layout_constraintEnd_toStartOf="@id/logo_market"
    app:layout_constraintHorizontal_bias="0"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
"""
                )
            ).run()
            .expectClean()
    }

    @Test
    fun `expect textSize fail`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """    
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/name_market"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginEnd="@dimen/keyline_3"
    android:layout_marginBottom="@dimen/keyline_0"
    android:ellipsize="end"
    android:gravity="left"
    android:textAppearance="?attr/textAppearanceBody1"
    android:textColor="?colorPrimary"
    android:textSize="@dimen/market_item_title_size"
    app:layout_constraintBottom_toTopOf="@id/delivery_time"
    app:layout_constraintEnd_toStartOf="@id/logo_market"
    app:layout_constraintHorizontal_bias="0"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
"""
                )
            ).run()
            .expect(
                """res/layout/layout.xml:13: Error: Use textAppearance instead [HardcodedTextSizeDetector]
    android:textSize="@dimen/market_item_title_size"
                      ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings"""
            )
    }

    @Test
    fun `expect textStyle fail`() {
        lint()
            .files(
                xml(
                    "res/layout/layout.xml", """    
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/name_market"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginEnd="@dimen/keyline_3"
    android:layout_marginBottom="@dimen/keyline_0"
    android:ellipsize="end"
    android:gravity="left"
    android:textAppearance="?attr/textAppearanceBody1"
    android:textColor="?colorPrimary"
    android:textStyle="bold"
    app:layout_constraintBottom_toTopOf="@id/delivery_time"
    app:layout_constraintEnd_toStartOf="@id/logo_market"
    app:layout_constraintHorizontal_bias="0"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
"""
                )
            ).run()
            .expect(
                """res/layout/layout.xml:13: Error: Use textAppearance instead [HardcodedTextSizeDetector]
    android:textStyle="bold"
                       ~~~~
1 errors, 0 warnings"""
            )
    }
}