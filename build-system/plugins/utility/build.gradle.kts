@file:Suppress("UnstableApiUsage")

plugins {
    id("org.gradle.kotlin.kotlin-dsl")
}

group = "com.orcchg.stockshock.infra.plugins"

gradlePlugin {
    plugins.register("utility") {
        id = "utility"
        implementationClass = "com.orcchg.stockshock.infra.plugins.utility.UtilityPlugin"
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.plugin.android.gradle) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
