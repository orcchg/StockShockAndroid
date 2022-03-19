plugins {
    `kotlin-dsl`
}

group = "com.orcchg.stockshock.infra"

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.plugin.android.cachefix)
    implementation(libs.plugin.android.gradle) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
    implementation(libs.plugin.kotlin.gradle)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(project(":kotlin-convention"))
    implementation(project(":plugins:utility"))
}
