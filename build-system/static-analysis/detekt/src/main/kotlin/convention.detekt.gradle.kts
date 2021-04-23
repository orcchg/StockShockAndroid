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
        xml.enabled = true
        txt.enabled = true
    }
}

dependencies {
    detektPlugins(libs.detektFormatting)
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
//    dependsOn(":detekt-rules:assemble")
    jvmTarget = "1.8"
}
