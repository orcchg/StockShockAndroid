pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.jlleitschuh.gradle.ktlint") {
                useModule("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")
            }
            if (requested.id.id == "com.diffplug.spotless") {
                useModule("com.diffplug.spotless:spotless-plugin-gradle:5.12.2")
            }
        }
    }
}

rootProject.name = "static-analysis"

include(":detekt")
include(":detekt-rules")
include(":ktlint")
include(":spotless")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
