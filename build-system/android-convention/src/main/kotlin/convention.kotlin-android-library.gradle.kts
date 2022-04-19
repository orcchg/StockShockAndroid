plugins {
    id("com.android.library")
    id("kotlin-android")
    id("internals.kotlin-base")
    id("internals.android-base")
    id("convention.jacoco-android")
    id("convention.sonarqube-android")
}

android {
    /**
     * Ignore all buildTypes instead of release for com.android.library modules
     * Also configure fallbacks for dependent modules
     */
    variantFilter {
        if (name != "release") {
            ignore = true
        }
    }
}
