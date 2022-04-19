plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("internals.kotlin-base")
    id("internals.android-base")
    id("convention.jacoco-android")
    id("convention.sonarqube-android")
}

android {
    testBuildType = "debug"

    buildTypes {
        getByName(testBuildType) {
            matchingFallbacks += listOf("release")
        }
    }
}
