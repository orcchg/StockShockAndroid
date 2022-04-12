import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarQubeExtension

plugins {
    id("org.sonarqube") // plugin appliance is idempotent
}

configure<SonarQubeExtension> {
    properties {
        property("sonar.sources", "$projectDir/src/main")
        property("sonar.java.coveragePlugin", "jacoco")
    }
}
