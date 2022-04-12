import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarQubeExtension

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
