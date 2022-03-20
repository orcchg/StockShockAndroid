import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.orcchg.stockshock.infra.plugins.utility.withVersionCatalogs

plugins {
    id("convention.detekt")
    id("convention.ktlint")
    id("convention.spotless")
}

withVersionCatalogs {
    dependencies {
        add("implementation", kotlin.reflect)
        add("implementation", kotlin.stdlib)
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = versions.javaVersion.get()
            allWarningsAsErrors = false
        }
    }
}
