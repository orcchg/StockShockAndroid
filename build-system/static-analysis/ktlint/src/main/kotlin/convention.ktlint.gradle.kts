import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("org.jlleitschuh.gradle.ktlint")
}

withVersionCatalogs {
    configure<KtlintExtension> {
        setVersion(versions.version.debugging.ktlint.get())
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(false)
        enableExperimentalRules.set(false)
        disabledRules.addAll(
            "final-newline",
            "import-ordering",
            "parameter-list-wrapping",
            "indent"
        )
        filter {
            exclude("**/generated/**")
            exclude("**/script/**")
            include("**/kotlin/**")
        }
    }
}