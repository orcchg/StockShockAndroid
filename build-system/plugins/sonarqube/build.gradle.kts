plugins {
    id("org.gradle.kotlin.kotlin-dsl")
}

group = "com.orcchg.stockshock.infra.plugins"

dependencies {
    implementation(libs.plugin.sonar)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
