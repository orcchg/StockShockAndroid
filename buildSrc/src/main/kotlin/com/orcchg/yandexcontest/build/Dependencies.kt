package com.orcchg.yandexcontest.build

object Versions {
    const val minSdkVersion = 23
    const val targetSdkVersion = 30
    const val compileSdkVersion = 30

    const val appCompat = "1.2.0"
    const val appStartup = "1.0.0"
    const val autoDispose = "1.4.0"
    const val buildToolsVersion = "30.0.0"
    const val constraintLayout = "2.0.0-rc1"
    const val coreKtx = "1.3.1"
    const val dagger = "2.34.1"
    const val detekt = "1.16.0"
    const val espresso = "3.2.0"
    const val finnhub = "1.2.0"
    const val fragmentKtx = "1.3.2"
    const val glide = "4.12.0"
    const val gradleAndroid = "4.1.3"
    const val hamcrest = "1.3"
    const val javaBuildConfig = "1.1.8"
    const val javaxInject = "1"
    const val junit = "4.13.2"
    const val junitAndroidExt = "1.1.1"
    const val ktlint = "0.38.0"
    const val ktlintGradle = "9.4.1"
    const val ktStdLib = "1.4.32"
    const val leakCanary = "2.7"
    const val lifecycle = "2.3.1"
    const val lint = "27.0.1"
    const val material = "1.3.0"
    const val moshi = "1.12.0"
    const val navigation = "2.3.5"
    const val okHttp = "4.8.1"
    const val recyclerView = "1.2.0"
    const val retrofit = "2.9.0"
    const val room = "2.2.6"
    const val rx = "2.2.21"
    const val rxAndroid = "2.1.1"
    const val rxBinding = "3.1.0"
    const val rxKt = "2.4.0"
    const val scarlet = "0.1.12"
    const val shimmer = "0.5.0"
    const val spotless = "5.12.1"
    const val stetho = "1.6.0"
    const val swipeRefreshLayout = "1.1.0"
    const val versionsPlugin = "0.38.0"
    const val viewPager2 = "1.0.0"
    const val timber = "4.7.1"
}

object Dependencies {

    object AndroidX {
        @JvmStatic val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        @JvmStatic val appStartup = "androidx.startup:startup-runtime:${Versions.appStartup}"
        @JvmStatic val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        @JvmStatic val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
        @JvmStatic val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        @JvmStatic val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        @JvmStatic val material = "com.google.android.material:material:${Versions.material}"
        @JvmStatic val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        @JvmStatic val navigationDynamicFeatures = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
        @JvmStatic val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        @JvmStatic val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        @JvmStatic val room = "androidx.room:room-runtime:${Versions.room}"
        @JvmStatic val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        @JvmStatic val roomRx = "androidx.room:room-rxjava2:${Versions.room}"
    }

    object Di {
        @JvmStatic val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        @JvmStatic val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        @JvmStatic val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
    }

    object Gradle {
        @JvmStatic val androidGradle = "com.android.tools.build:gradle:${Versions.gradleAndroid}"
        @JvmStatic val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
        @JvmStatic val javaBuildConfig = "gradle.plugin.de.fuerstenau:BuildConfigPlugin:${Versions.javaBuildConfig}"
        @JvmStatic val ktGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.ktStdLib}"
        @JvmStatic val ktLint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlintGradle}"
        @JvmStatic val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
        @JvmStatic val versions = "com.github.ben-manes:gradle-versions-plugin:${Versions.versionsPlugin}"
    }

    object Lib {
        @JvmStatic val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        @JvmStatic val ktStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.ktStdLib}"
        @JvmStatic val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
        @JvmStatic val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Network {
        @JvmStatic val finnhub = "com.finnhub:kotlin-client:${Versions.finnhub}"
        @JvmStatic val okHttpLog = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        @JvmStatic val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        @JvmStatic val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        @JvmStatic val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
        @JvmStatic val scarlet = "com.tinder.scarlet:scarlet:${Versions.scarlet}"
        @JvmStatic val scarletAndroid = "com.tinder.scarlet:lifecycle-android:${Versions.scarlet}"
        @JvmStatic val scarletMoshi = "com.tinder.scarlet:message-adapter-moshi:${Versions.scarlet}"
        @JvmStatic val scarletOkHttp = "com.tinder.scarlet:websocket-okhttp:${Versions.scarlet}"
        @JvmStatic val scarletRx = "com.tinder.scarlet:stream-adapter-rxjava2:${Versions.scarlet}"
        @JvmStatic val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
        @JvmStatic val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    }

    object Parser {
        @JvmStatic val moshi = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
        @JvmStatic val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        @JvmStatic val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Plugin {
        const val androidApp = "com.android.application"
        const val androidDynamicFeature = "com.android.dynamic-feature"
        const val androidLib = "com.android.library"
        const val detekt = "io.gitlab.arturbosch.detekt"
        const val javaBuildConfig = "de.fuerstenau.buildconfig"
        const val javaLib = "java-library"
        const val google = "com.google.gms.google-services"
        const val kotlin = "kotlin"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinAnnotation = "kotlin-kapt"
        const val ktlint = "org.jlleitschuh.gradle.ktlint"
        const val navSafeArgs = "androidx.navigation.safeargs.kotlin"
        const val spotless = "com.diffplug.spotless"
        const val versions = "com.github.ben-manes.versions"
    }

    object Rx {
        @JvmStatic val autoDispose = "com.uber.autodispose:autodispose:${Versions.autoDispose}"
        @JvmStatic val autoDisposeAndroidAC = "com.uber.autodispose:autodispose-android-archcomponents:${Versions.autoDispose}"
        @JvmStatic val rx = "io.reactivex.rxjava2:rxjava:${Versions.rx}"
        @JvmStatic val rxKt = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKt}"
        @JvmStatic val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
        @JvmStatic val rxBinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"
        @JvmStatic val rxBindingCore = "com.jakewharton.rxbinding3:rxbinding-core:${Versions.rxBinding}"
        @JvmStatic val rxBindingMaterial = "com.jakewharton.rxbinding3:rxbinding-material:${Versions.rxBinding}"
        @JvmStatic val rxBindingSwipe = "com.jakewharton.rxbinding3:rxbinding-swiperefreshlayout:${Versions.rxBinding}"
    }

    object View {
        @JvmStatic val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        @JvmStatic val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        @JvmStatic val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
        @JvmStatic val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
        @JvmStatic val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"
    }

    object Lint {
        @JvmStatic val detektApi = "io.gitlab.arturbosch.detekt:detekt-api:${Versions.detekt}"
        @JvmStatic val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"
        @JvmStatic val detektTest = "io.gitlab.arturbosch.detekt:detekt-test:${Versions.detekt}"
        @JvmStatic val lintApi = "com.android.tools.lint:lint-api:${Versions.lint}"
        @JvmStatic val lintChecks = "com.android.tools.lint:lint-checks:${Versions.lint}"
        @JvmStatic val lintCore = "com.android.tools.lint:lint:${Versions.lint}"
        @JvmStatic val lintTests = "com.android.tools.lint:lint-tests:${Versions.lint}"
    }

    object AndroidTest {
        @JvmStatic val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        @JvmStatic val junit = "androidx.test.ext:junit:${Versions.junitAndroidExt}"
    }

    object Test {
        @JvmStatic val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
        @JvmStatic val junit = "junit:junit:${Versions.junit}"
    }
}
