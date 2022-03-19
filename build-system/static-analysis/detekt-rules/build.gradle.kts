plugins {
    `kotlin-dsl`
}

group = "com.orcchg.stockshock.infra"

dependencies {
    implementation(libs.debugging.detekt.api)

    testImplementation(libs.test.detekt)
    testImplementation(libs.test.junit)
}
