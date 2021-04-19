plugins {
    id("com.android.application")
    id("kotlin-android")
    id("convention.kotlin-base")
    id("convention.android-base")
}

@Suppress("UnstableApiUsage")
android {
    testBuildType = "debug"
    /**
     * Disable all buildTypes except testing
     * to avoid confusing errors in IDE if wrong build variant is selected
     */
    variantFilter {
        if (name != testBuildType) {
            ignore = true
            logger.debug("Build variant $name is omitted for module: $path")
        }
    }

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
