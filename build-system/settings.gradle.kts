pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

rootProject.name = "build-system"

includeBuild("libraries")

include(":git-hooks")
include(":kotlin-convention")
include(":android-convention")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        google()
    }
}
