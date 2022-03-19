package com.orcchg.stockshock.infra.plugins.utility

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.the
import java.io.File

fun Project.sources(): Set<File>? =
    if (this.hasProperty("android")) {
        val androidExtension = this.extensions.findByName("android") as BaseExtension
        androidExtension.sourceSets.named("main").orNull?.java?.srcDirs
    } else {
        (this.extensions.findByName("sourceSets") as SourceSetContainer)
            .named("main").orNull?.java?.srcDirs
    }

/**
 * Make 'catalog' extension (for version catalog) accessible from convention plugins.
 *
 * https://github.com/gradle/gradle/issues/15383
 */
inline fun Project.withVersionCatalogs(block: LibrariesForLibs.() -> Unit) {
    if (name != "gradle-kotlin-dsl-accessors") {
        val libs = the<LibrariesForLibs>()
        block(libs)
    }
}
