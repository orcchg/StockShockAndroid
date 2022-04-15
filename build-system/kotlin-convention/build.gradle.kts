plugins {
    id("org.gradle.kotlin.kotlin-dsl")
}

group = "com.orcchg.stockshock.infra"

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.plugin.kotlin.gradle) // to access 'kotlin' plugin further
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(project(":plugins:android-jar"))
    implementation(project(":plugins:jacoco"))
    implementation(project(":plugins:sonarqube"))
    implementation(project(":plugins:utility"))
    implementation(project(":static-analysis:detekt"))
    implementation(project(":static-analysis:ktlint"))
    implementation(project(":static-analysis:spotless"))
}
