pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

rootProject.name = "build-system"

include(":git-hooks")
