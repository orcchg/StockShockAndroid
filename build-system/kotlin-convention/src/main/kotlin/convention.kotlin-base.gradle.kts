import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("convention.libraries")
    id("convention.detekt")
    id("convention.ktlint")
//    id("convention.spotless") // TODO: spotless plugin has errors
}

dependencies {
    add("implementation", libs.kotlinReflect)
    add("implementation", libs.kotlinStdLib)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = libs.javaVersion.toString()
        allWarningsAsErrors = true
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}
