plugins {
    id("org.gradle.kotlin.kotlin-dsl")
}

group = "com.orcchg.stockshock.infra.plugins"

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(project(":plugins:utility"))
}
