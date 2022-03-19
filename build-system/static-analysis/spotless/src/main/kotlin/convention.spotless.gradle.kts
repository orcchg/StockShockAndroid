import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs

plugins {
    id("com.diffplug.spotless")
}

withVersionCatalogs {
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
            ktlint(versions.version.debugging.ktlint.get())
        }
    }
}
