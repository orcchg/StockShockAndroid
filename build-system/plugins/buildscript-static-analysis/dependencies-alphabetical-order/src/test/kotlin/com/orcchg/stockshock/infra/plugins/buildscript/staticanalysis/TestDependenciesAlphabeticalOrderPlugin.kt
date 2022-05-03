package com.orcchg.stockshock.infra.plugins.buildscript.staticanalysis

import org.gradle.internal.impldep.org.hamcrest.CoreMatchers.`is`
import org.gradle.internal.impldep.org.hamcrest.CoreMatchers.equalTo
import org.gradle.internal.impldep.org.hamcrest.MatcherAssert.assertThat
import org.gradle.internal.impldep.org.junit.Before
import org.gradle.internal.impldep.org.junit.Rule
import org.gradle.internal.impldep.org.junit.Test
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Assert.assertTrue
import java.io.File

class TestDependenciesAlphabeticalOrderPlugin {

    @Rule val testProjectDir = TemporaryFolder()

    private lateinit var buildFile: File

    @Before
    fun setup() {
        val buildscript = """
            plugins {
                id("dependencies-alphabetical-order")
            }
        """.trimIndent()

        buildFile = testProjectDir.newFile("build.gradle.kts")
        buildFile.appendText(buildscript)
    }

    @Test
    fun `small - dependencies honor alphabetical order - success`() {
        val buildscript = """
            dependencies {
                implementation("androidx.cardview:cardview:1.0.0")
                implementation("com.github.bumptech.glide:glide:4.12.0")
            }
        """.trimIndent()
        buildFile.appendText(buildscript)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments(DependenciesAlphabeticalOrderPlugin.TASK_NAME)
            .withPluginClasspath()
            .build()

        assertThat(result.task(DependenciesAlphabeticalOrderPlugin.TASK_NAME)!!.outcome, `is`(equalTo(TaskOutcome.SUCCESS)))
    }

    @Test
    fun `small - dependencies dishonor alphabetical order - report failure`() {
        val buildscript = """
            dependencies {
                implementation("com.github.bumptech.glide:glide:4.12.0")
                implementation("androidx.cardview:cardview:1.0.0")
            }
        """.trimIndent()
        buildFile.appendText(buildscript)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withArguments(DependenciesAlphabeticalOrderPlugin.TASK_NAME)
            .withPluginClasspath()
            .build()

        val errorReport = """
            --->  'com.github.bumptech.glide:glide'  is followed by  'androidx.cardview:cardview'
        """.trimIndent()

        assertTrue(false)
        assertTrue(result.output.contains(errorReport))
        assertThat(result.task(DependenciesAlphabeticalOrderPlugin.TASK_NAME)!!.outcome, `is`(equalTo(TaskOutcome.FAILED)))
    }
}
