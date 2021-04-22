import com.orcchg.yandexcontest.infra.libraries.Versions

plugins {
    id("convention.libraries")
    id("com.diffplug.spotless")
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    format("misc") {
        target("*.gradle", "*.md", ".gitignore")
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    java {
        target("src/*/java/**/*.java")
        importOrder() // standard import order
        removeUnusedImports()
        googleJavaFormat("1.8").aosp()
    }
    kotlin {
        target("src/*/java/**/*.kt")
        ktlint(Versions.ktlint)
    }
}
