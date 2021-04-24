import io.gitlab.arturbosch.detekt.Detekt

plugins {
    `kotlin-dsl`
    id("convention.detekt")
    id("git-hooks-install")
    id("com.github.ben-manes.versions") version "0.38.0"
}

subprojects {
    apply(plugin = "convention.detekt")

    dependencies {
        detektPlugins(project(":static_analysis:detekt-rules"))
    }

    tasks.withType<Detekt>().configureEach {
        dependsOn(":static_analysis:detekt-rules:assemble")
    }
}

rootProject.gradle.startParameter.setTaskNames(
    rootProject.gradle.startParameter.taskNames + "gitHooksInstall"
)

apply("$rootDir/scripts/gradle/project_dependency_graph.gradle")
