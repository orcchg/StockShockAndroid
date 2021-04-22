plugins {
    `kotlin-dsl`
    id("convention.libraries")
}

group = "com.orcchg.yandexcontest.infra.staticanalysis"

dependencies {
    implementation("com.orcchg.yandexcontest.infra:libraries")
    implementation(libs.ktlintPlugin)
}
