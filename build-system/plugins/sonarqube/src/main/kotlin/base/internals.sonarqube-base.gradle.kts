import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarQubeExtension

plugins {
    id("org.sonarqube") // plugin appliance is idempotent
}

configure<SonarQubeExtension> {
    properties {
        property("sonar.sources", "$projectDir/src/main/kotlin,$projectDir/src/main/java")
        property("sonar.java.coveragePlugin", "jacoco")
    }
}
