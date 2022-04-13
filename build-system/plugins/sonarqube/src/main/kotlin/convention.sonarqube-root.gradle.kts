import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarQubeExtension
import org.sonarqube.gradle.SonarQubeTask
import java.util.stream.Collectors

/**
 * Convention plugin, which configures SonarQube the root project.
 */

plugins {
    id("internals.sonarqube-base")
}

description = "Plugin, which configures SonarQube for the root project"

configure<SonarQubeExtension> {
    properties {
        property("sonar.host.url", "https://sonarcloud.io/")
        property("sonar.organization", "orcchg")
        property("sonar.projectKey", "orcchg_StockShockAndroid")
        property("sonar.verbose", true)
    }
}

/**
 * Set root project's [SonarQubeTask] depend on set of [JacocoReport] tasks
 * collected from all relevant subprojects.
 */
tasks.withType<SonarQubeTask>().configureEach {
    rootProject.subprojects.stream()
        .filter { p ->
            val sonarQubeExtension = p.extensions.findByType<SonarQubeExtension>()
            p.plugins.hasPlugin(JacocoPlugin::class) &&
            sonarQubeExtension != null && !sonarQubeExtension.isSkipProject
        }
        .map { p -> p.tasks.withType<JacocoReport>() }
        .flatMap(Collection<JacocoReport>::stream)
        .collect(Collectors.toList())
}
