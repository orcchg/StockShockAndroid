package com.orcchg.yandexcontest.infra.libraries

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
        @JvmStatic val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
        @JvmStatic val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
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
