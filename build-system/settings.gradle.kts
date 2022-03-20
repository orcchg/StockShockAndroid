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
                useModule("org.gradle.kotlin:gradle-kotlin-dsl-plugins:2.1.7")
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

include(":git-hooks")
include(":kotlin-convention")
include(":android-convention")
include(":plugins:android-jar")
include(":plugins:utility")
include(":static-analysis:detekt")
include(":static-analysis:detekt-rules")
include(":static-analysis:ktlint")
include(":static-analysis:spotless")
