plugins {
    `kotlin-dsl`
    id("convention.libraries")
}

group = "com.orcchg.yandexcontest.infra.buildsystem"

dependencies {
    implementation("com.orcchg.yandexcontest.infra:libraries")
    implementation("com.orcchg.yandexcontest.infra.staticanalysis:static-analysis")
    implementation(libs.kotlinPlugin)
}
