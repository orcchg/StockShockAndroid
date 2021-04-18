pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

rootProject.name = "build-system"

include(":git-hooks")
include(":android-convention")
include(":kotlin-convention")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}
