plugins {
    `kotlin-dsl`
    id("convention.libraries")
}

group = "com.orcchg.yandexcontest.infra.buildsystem"

dependencies {
    implementation("com.orcchg.yandexcontest.infra:libraries")
    implementation("com.orcchg.yandexcontest.infra.staticanalysis:detekt")
    implementation("com.orcchg.yandexcontest.infra.staticanalysis:ktlint")
    implementation("com.orcchg.yandexcontest.infra.staticanalysis:spotless")
    implementation(libs.kotlinPlugin) // to access 'kotlin' plugin further
}
