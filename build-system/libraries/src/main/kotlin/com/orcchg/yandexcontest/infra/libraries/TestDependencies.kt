package com.orcchg.yandexcontest.infra.libraries

abstract class TestDependencies {

    // android test
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val junitAndroid = "androidx.test.ext:junit:${Versions.junitAndroidExt}"

    // test
    val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
    val junit = "junit:junit:${Versions.junit}"

    // misc
    val detektTest = "io.gitlab.arturbosch.detekt:detekt-test:${Versions.detekt}"
    val lintTests = "com.android.tools.lint:lint-tests:${Versions.lint}"
}
