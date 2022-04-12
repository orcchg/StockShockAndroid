plugins {
    id("com.android.library")
    id("kotlin-android")
    id("internals.kotlin-base")
    id("internals.android-base")
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
