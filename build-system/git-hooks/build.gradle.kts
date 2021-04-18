plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "com.orcchg.yandexcontest.infra.buildsystem"

repositories {
    gradlePluginPortal()
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

gradlePlugin {
    plugins.register("git-hooks-install") {
        id = "git-hooks-install"
        implementationClass = "com.orcchg.yandexcontest.infra.githooks.GitHooksInstallPlugin"
    }
}

dependencies {
    gradleApi()
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
}
