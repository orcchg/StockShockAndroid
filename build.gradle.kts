buildscript {
    dependencies {
        classpath(libs.plugin.android.cachefix)
        classpath(libs.plugin.android.gradle)
        classpath(libs.plugin.sonar)
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.51.0"
    id("com.autonomousapps.dependency-analysis") version "1.32.0"
    id("convention.jacoco-root")
    id("convention.sonarqube-root")
    id("git-hooks-install")
    id("com.osacky.doctor") version "0.10.0"
}

doctor {
    allowBuildingAllAndroidAppsSimultaneously.set(true)
    javaHome {
        failOnError.set(false)
    }
}

//rootProject.gradle.startParameter.setTaskNames(
//    rootProject.gradle.startParameter.taskNames + "gitHooksInstall"
//)

apply("$rootDir/scripts/gradle/project_dependency_graph.gradle")
