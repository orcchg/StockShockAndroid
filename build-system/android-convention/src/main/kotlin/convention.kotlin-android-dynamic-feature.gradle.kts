plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("convention.kotlin-base")
    id("convention.android-base")
    id("convention.ktlint")
}

android {
    testBuildType = "debug"

    buildTypes {
        getByName(testBuildType) {
            matchingFallbacks += listOf("release")
        }
    }
}
