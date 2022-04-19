buildscript {
    repositories {
        // for 'com.vanniktech:gradle-android-junit-jacoco-plugin:0.17.0-SNAPSHOT'
        maven { setUrl(uri("https://oss.sonatype.org/content/repositories/snapshots")) }
    }
    dependencies {
        classpath(libs.plugin.android.cachefix)
        classpath(libs.plugin.android.gradle)
        classpath(libs.plugin.sonar)
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.38.0"
    id("convention.jacoco-root")
    id("convention.sonarqube-root")
    id("git-hooks-install")
}

//rootProject.gradle.startParameter.setTaskNames(
//    rootProject.gradle.startParameter.taskNames + "gitHooksInstall"
//)

apply("$rootDir/scripts/gradle/project_dependency_graph.gradle")
