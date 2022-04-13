import org.sonarqube.gradle.SonarQubeExtension

/**
 * Convention plugin, which configures SonarQube for Android subprojects.
 */

plugins {
    id("internals.sonarqube-base")
}

description = "Plugin, which configures SonarQube for this Android project"

configure<SonarQubeExtension> {
    androidVariant = "release"
    properties {
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "$buildDir/reports/jacoco/$androidVariant/jacoco.xml"
        )
        property(
            "sonar.java.binaries",
            "$buildDir/intermediates/javac/$androidVariant/classes"
        )
    }
}
