import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("convention.libraries")
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    parallel = true
    ignoreFailures = false
    val configFilePath = "${rootProject.projectDir}/build-system/static-analysis/detekt/config/detekt.yml"
    if (file(configFilePath).exists()) {
        config = files(configFilePath)
    }
    baseline = file("$projectDir/config/detekt-baseline.xml")

    reports {
        html.enabled = true
        xml.enabled = false
        txt.enabled = false
    }
}

dependencies {
    detektPlugins(libs.detektFormatting)
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
