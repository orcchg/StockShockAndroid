import com.orcchg.yandexcontest.infra.libraries.Versions

plugins {
    id("convention.libraries")
    id("org.jlleitschuh.gradle.ktlint")
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    setVersion(Versions.ktlint)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)
    enableExperimentalRules.set(false)
    disabledRules.addAll("final-newline", "import-ordering", "parameter-list-wrapping", "indent")
    filter {
        exclude("**/generated/**")
        exclude("**/script/**")
        include("**/kotlin/**")
    }
}
