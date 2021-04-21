plugins {
    id("com.android.library")
    id("kotlin-android")
    id("convention.kotlin-base")
    id("convention.android-base")
    id("convention.ktlint")
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
