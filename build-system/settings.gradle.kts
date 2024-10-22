@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.gradle.kotlin.kotlin-dsl")) {
                useModule("org.gradle.kotlin:gradle-kotlin-dsl-plugins:4.5.0")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-system"

include(":kotlin-convention")
include(":android-convention")
include(":plugins:android-jar")
include(":plugins:buildscript-static-analysis:dependencies-alphabetical-order")
include(":plugins:git-hooks")
include(":plugins:jacoco")
include(":plugins:sonarqube")
include(":plugins:utility")
include(":static-analysis:detekt")
include(":static-analysis:detekt-rules")
include(":static-analysis:ktlint")
include(":static-analysis:spotless")
