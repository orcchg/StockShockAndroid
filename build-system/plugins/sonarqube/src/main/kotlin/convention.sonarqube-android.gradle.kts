import org.sonarqube.gradle.SonarQubeExtension

/**
 * Convention plugin, which configures SonarQube for Android subprojects.
 */

plugins {
    id("internals.sonarqube-base")
}

description = "Plugin, which configures SonarQube for this Android project"

configure<SonarQubeExtension> {
    properties {
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "$buildDir/reports/jacoco/release/jacoco.xml,$buildDir/reports/jacoco/debug/jacoco.xml"
        )
        property(
            "sonar.java.binaries",
            "$buildDir/intermediates/javac/release/classes,$buildDir/intermediates/javac/debug/classes"
        )
    }
}
