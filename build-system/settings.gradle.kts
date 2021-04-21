pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.4.32")
            }
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:4.1.3")
            }
        }
    }
}

rootProject.name = "build-system"

includeBuild("libraries")
includeBuild("static-analysis")

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
