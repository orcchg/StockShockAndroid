import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * Convention plugin, which configures JaCoCo for pure kotlin subprojects.
 *
 * @see https://docs.gradle.org/current/userguide/jacoco_plugin.html
 */

plugins {
    id("convention.jacoco")
}

description = "Plugin, which configures JaCoCo for pure kotlin subprojects"

tasks.withType(JacocoReport::class).configureEach {
    // tests are required to run before generating the report
    dependsOn(tasks.named("test"))
}
