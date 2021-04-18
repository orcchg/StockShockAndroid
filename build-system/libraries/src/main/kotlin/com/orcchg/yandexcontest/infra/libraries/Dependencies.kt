package com.orcchg.yandexcontest.infra.libraries

import org.gradle.api.JavaVersion

abstract class Dependencies {

    val javaVersion = JavaVersion.VERSION_1_8
    val minSdkVersion = 23
    val targetSdkVersion = 30
    val compileSdkVersion = 30
    val buildToolsVersion = "29.0.3"

    // android
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val appStartup = "androidx.startup:startup-runtime:${Versions.appStartup}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val material = "com.google.android.material:material:${Versions.material}"
    val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationDynamicFeatures = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
    val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    val room = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomRx = "androidx.room:room-rxjava2:${Versions.room}"

    // di
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"

    // gradle
    val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradleAndroid}"
    val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
    val javaBuildConfig = "gradle.plugin.de.fuerstenau:BuildConfigPlugin:${Versions.javaBuildConfig}"
    val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.ktStdLib}"
    val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlintGradle}"
    val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
    val versions = "com.github.ben-manes:gradle-versions-plugin:${Versions.versionsPlugin}"

    // libraries
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.ktStdLib}"
    val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // network
    val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    val finnhub = "com.finnhub:kotlin-client:${Versions.finnhub}"
    val okHttpLog = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val scarlet = "com.tinder.scarlet:scarlet:${Versions.scarlet}"
    val scarletAndroid = "com.tinder.scarlet:lifecycle-android:${Versions.scarlet}"
    val scarletMoshi = "com.tinder.scarlet:message-adapter-moshi:${Versions.scarlet}"
    val scarletOkHttp = "com.tinder.scarlet:websocket-okhttp:${Versions.scarlet}"
    val scarletRx = "com.tinder.scarlet:stream-adapter-rxjava2:${Versions.scarlet}"
    val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    val stethoOkHttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

    // parser
    val moshi = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

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

    // rx
    val autoDispose = "com.uber.autodispose:autodispose:${Versions.autoDispose}"
    val autoDisposeAndroidAC = "com.uber.autodispose:autodispose-android-archcomponents:${Versions.autoDispose}"
    val rx = "io.reactivex.rxjava2:rxjava:${Versions.rx}"
    val rxKt = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKt}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val rxBinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"
    val rxBindingCore = "com.jakewharton.rxbinding3:rxbinding-core:${Versions.rxBinding}"
    val rxBindingMaterial = "com.jakewharton.rxbinding3:rxbinding-material:${Versions.rxBinding}"
    val rxBindingSwipe = "com.jakewharton.rxbinding3:rxbinding-swiperefreshlayout:${Versions.rxBinding}"

    // view
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"

    // lint
    val detektApi = "io.gitlab.arturbosch.detekt:detekt-api:${Versions.detekt}"
    val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"
    val detektTest = "io.gitlab.arturbosch.detekt:detekt-test:${Versions.detekt}"
    val lintApi = "com.android.tools.lint:lint-api:${Versions.lint}"
    val lintChecks = "com.android.tools.lint:lint-checks:${Versions.lint}"
    val lintCore = "com.android.tools.lint:lint:${Versions.lint}"
    val lintTests = "com.android.tools.lint:lint-tests:${Versions.lint}"
}
