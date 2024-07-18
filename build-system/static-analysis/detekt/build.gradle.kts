plugins {
    `kotlin-dsl`
}

group = "com.orcchg.stockshock.infra"

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.plugin.detekt)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(project(":plugins:utility"))
}
