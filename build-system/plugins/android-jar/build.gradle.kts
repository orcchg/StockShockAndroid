plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "com.orcchg.stockshock.infra.plugins"

gradlePlugin {
    plugins.register("android-jar") {
        id = "android-jar"
        implementationClass = "com.orcchg.stockshock.infra.plugins.androidjar.AndroidJarPlugin"
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.plugin.kotlin.gradle)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(project(":plugins:utility"))
}
