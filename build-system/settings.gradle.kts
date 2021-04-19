pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.4.31")
            }
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:4.1.3")
            }
            if (requested.id.id == "org.jlleitschuh.gradle.ktlint") {
                useModule("org.jlleitschuh.gradle:ktlint-gradle:9.4.1")
            }
            if (requested.id.id == "com.diffplug.spotless") {
                useModule("com.diffplug.spotless:spotless-plugin-gradle:5.12.1")
            }
        }
    }
}

rootProject.name = "build-system"

includeBuild("libraries")

include(":git-hooks")
include(":kotlin-convention")
include(":android-convention")
include(":static-analysis")

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
