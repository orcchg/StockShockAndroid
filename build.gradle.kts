buildscript {
    dependencies {
        classpath(libs.plugin.android.cachefix)
        classpath(libs.plugin.android.gradle)
    }
}

plugins {
    id("git-hooks-install")
    id("com.github.ben-manes.versions") version "0.38.0"
}

//rootProject.gradle.startParameter.setTaskNames(
//    rootProject.gradle.startParameter.taskNames + "gitHooksInstall"
//)

apply("$rootDir/scripts/gradle/project_dependency_graph.gradle")
