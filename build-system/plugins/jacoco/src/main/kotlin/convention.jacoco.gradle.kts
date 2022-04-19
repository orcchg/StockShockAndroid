import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension

/**
 * Convention plugin, which configures JaCoCo for subprojects.
 *
 * @see https://docs.gradle.org/current/userguide/jacoco_plugin.html
 */

plugins {
    id("jacoco")
}

description = "Plugin, which configures JaCoCo for subprojects"

withVersionCatalogs {
    configure<JacocoPluginExtension> {
        toolVersion = versions.jacoco.get()
    }
}

tasks.withType(JacocoReport::class).configureEach {
    reports {
        xml.required.set(true)
    }
}

tasks.withType<Test>().configureEach {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}
