import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    id("convention.jacoco")
}

tasks.register("jacocoTestReportAll").configure {
    group = "Reporting"
    description = "Run all jacocoTestReport tasks"

    rootProject.subprojects.forEach { p ->
        dependsOn(p.tasks.withType<JacocoReport>())
    }
}
