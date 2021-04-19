pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:7.0.0-alpha14")
            }
        }
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
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
