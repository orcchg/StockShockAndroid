import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarQubeExtension

/**
 * Convention plugin, which configures SonarQube for pure Java/Kotlin subprojects.
 */

plugins {
    id("internals.sonarqube-base")
}

description = "Plugin, which configures SonarQube for this pure Java/Kotlin project"

configure<SonarQubeExtension> {
    properties {
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "$buildDir/reports/jacoco/test/jacocoTestReport.xml,$buildDir/reports/jacoco/jacoco.xml"
        )
        property(
            "sonar.java.binaries",
            "$buildDir/classes"
        )
    }
}
