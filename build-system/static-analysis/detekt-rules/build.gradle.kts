plugins {
    `kotlin-dsl`
}

group = "com.orcchg.stockshock.infra"

dependencies {
    implementation(libs.debugging.detekt.api)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.test.detekt)
    testImplementation(libs.test.junit)
}
