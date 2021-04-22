plugins {
    `kotlin-dsl`
    id("convention.libraries")
}

group = "com.orcchg.yandexcontest.infra.staticanalysis"

dependencies {
    implementation("com.orcchg.yandexcontest.infra:libraries")
    implementation(libs.detektApi)

    testImplementation(testLibs.detektTest)
    testImplementation(testLibs.junit)
}
