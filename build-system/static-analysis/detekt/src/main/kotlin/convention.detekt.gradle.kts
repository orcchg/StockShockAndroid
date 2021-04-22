plugins {
    id("convention.libraries")
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    parallel = true
    ignoreFailures = false
    if (file("$rootProject.projectDir/static_analysis/detekt/config/detekt.yml").exists()) {
        config = files("$rootProject.projectDir/static_analysis/detekt/config/detekt.yml")
    }
    baseline = file("$projectDir/config/detekt-baseline.xml")

    reports {
        xml.enabled = true
        txt.enabled = true
    }
}

dependencies {
    detektPlugins(
        libs.detektFormatting
    )
    detektPlugins(
        project(":detekt-rules")
    )
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    setDependsOn(listOf(":detekt-rules:assemble"))
    jvmTarget = "1.8"
}
