plugins {
    id("com.android.application")
    id("kotlin-android")
    id("convention.kotlin-base")
    id("convention.android-base")
    id("convention.ktlint")
}

@Suppress("UnstableApiUsage")
android {
    testBuildType = "debug"

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName(testBuildType) {
            // libraries only built in release variant, see convention.kotlin-android-library
            matchingFallbacks += listOf("release")
        }
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}
