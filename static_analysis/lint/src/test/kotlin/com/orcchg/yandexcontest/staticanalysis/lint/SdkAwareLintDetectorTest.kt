package com.orcchg.yandexcontest.staticanalysis.lint

import com.android.testutils.TestUtils
import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask

abstract class SdkAwareLintDetectorTest : LintDetectorTest() {

    override fun lint(): TestLintTask =
        TestLintTask.lint().apply { sdkHome = sdk }

    companion object {
        val sdk = TestUtils.getSdk()
    }
}
