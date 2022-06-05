plugins {
    `java-gradle-plugin`
    id("org.gradle.kotlin.kotlin-dsl")
}

group = "com.orcchg.stockshock.infra"

gradlePlugin {
    plugins.register("git-hooks-install") {
        id = "git-hooks-install"
        implementationClass = "com.orcchg.yandexcontest.infra.githooks.GitHooksInstallPlugin"
    }
}

dependencies {
    gradleApi()
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.plugin.kotlin.gradle)
}
