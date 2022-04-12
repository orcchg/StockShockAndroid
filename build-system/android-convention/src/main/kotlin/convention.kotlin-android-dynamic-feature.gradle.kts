plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("internals.kotlin-base")
    id("internals.android-base")
}

android {
    testBuildType = "debug"

    buildTypes {
        getByName(testBuildType) {
            matchingFallbacks += listOf("release")
        }
    }
}
