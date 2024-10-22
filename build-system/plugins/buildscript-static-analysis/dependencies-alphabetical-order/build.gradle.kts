plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "com.orcchg.stockshock.infra.plugins"

gradlePlugin {
    plugins.register("dependencies-alphabetical-order") {
        id = "dependencies-alphabetical-order"
        implementationClass = "com.orcchg.stockshock.infra.plugins.buildscript.staticanalysis.DependenciesAlphabeticalOrderPlugin"
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.plugin.kotlin.gradle)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    testImplementation(gradleTestKit())
    testImplementation(libs.test.junit)
}
