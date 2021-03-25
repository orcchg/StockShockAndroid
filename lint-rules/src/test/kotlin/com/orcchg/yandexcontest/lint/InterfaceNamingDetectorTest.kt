package com.orcchg.yandexcontest.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.orcchg.yandexcontest.lint.rules.InterfaceNamingDetector
import org.junit.Test

class InterfaceNamingDetectorTest {

    @Test
    fun `interface without I prefix`() {
        TestLintTask.lint()
            .allowMissingSdk()
            .issues(InterfaceNamingDetector.ISSUE)
            .detector(InterfaceNamingDetector())
            .files(kotlin(WITHOUT_I_PREFIX_SOURCE)).run()
            .expect("No warnings.")
    }

    @Test
    fun `interface with I prefix`() {
        TestLintTask.lint()
            .allowMissingSdk()
            .issues(InterfaceNamingDetector.ISSUE)
            .detector(InterfaceNamingDetector())
            .files(kotlin(WITH_I_PREFIX_SOURCE))
            .run()
            .expect(WITH_I_PREFIX_RESULT)
    }

    @Test
    fun `object with I prefix`() {
        TestLintTask.lint()
            .allowMissingSdk()
            .issues(InterfaceNamingDetector.ISSUE)
            .detector(InterfaceNamingDetector())
            .files(kotlin(OBJECT_WITH_I_PREFIX_SOURCE))
            .run()
            .expect("No warnings.")
    }
}

private const val WITH_I_PREFIX_SOURCE = """
         package foo
         interface IExample
    """

private const val OBJECT_WITH_I_PREFIX_SOURCE = """
         package foo
         object IExample
    """

private const val WITHOUT_I_PREFIX_SOURCE = """
         package foo
         interface Example
    """

private const val WITH_I_PREFIX_RESULT = """
src/foo/IExample.kt:3: Error: Interface naming error [InterfaceNamingDetector]
         interface IExample
                   ~~~~~~~~
1 errors, 0 warnings
    """