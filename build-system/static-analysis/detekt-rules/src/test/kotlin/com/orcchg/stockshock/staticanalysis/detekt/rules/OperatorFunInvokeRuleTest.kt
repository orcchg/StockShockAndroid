package com.orcchg.stockshock.staticanalysis.detekt.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.Path
import java.nio.file.Paths

private const val CORRECT_SAMPLE = "DoSomethingInteractorCorrectSample.kt"
private const val INCORRECT_SAMPLE = "DoSomethingInteractorIncorrectSample.kt"

class OperatorFunInvokeRuleTest {
    @Test
    fun `operator fun invoke() is missing in file - no issues`() {
        // Prepare
        val path = getFileFromResources(CORRECT_SAMPLE)

        // Do
        val findings = OperatorFunInvokeRule().lint(path)

        // Check
        assertEquals(0, findings.size)
    }

    @Test
    fun `operator fun invoke() is found - lint error`() {
        // Prepare
        val path = getFileFromResources(INCORRECT_SAMPLE)

        // Do
        val findings = OperatorFunInvokeRule().lint(path)

        // Check
        assertEquals(1, findings.size)
        assertEquals("OperatorFunInvoke", findings.first().id)
    }

    private fun getFileFromResources(fileName: String): Path {
        val resource = javaClass.getResource(fileName)
        return Paths.get(resource.toURI())
    }
}
