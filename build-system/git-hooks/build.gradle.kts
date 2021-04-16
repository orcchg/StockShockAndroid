plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    gradleApi()
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
}
