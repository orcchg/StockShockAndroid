@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
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
