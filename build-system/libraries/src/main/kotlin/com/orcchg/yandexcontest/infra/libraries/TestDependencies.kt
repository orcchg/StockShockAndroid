package com.orcchg.yandexcontest.infra.libraries

abstract class TestDependencies {

    // android test
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val junitAndroid = "androidx.test.ext:junit:${Versions.junitAndroidExt}"

    // test
    val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
    val junit = "junit:junit:${Versions.junit}"
}
