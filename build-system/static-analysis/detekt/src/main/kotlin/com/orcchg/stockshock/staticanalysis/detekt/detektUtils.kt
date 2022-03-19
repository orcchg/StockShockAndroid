package com.orcchg.stockshock.staticanalysis.detekt

import java.io.File
import org.gradle.api.Project

fun findDetektBaselineAll(project: Project): File =
    try {
        val bs = project.gradle.includedBuild("build-system")
        File("${bs.projectDir}/../config/detekt-all/detekt-baseline-all.xml")
    } catch (e: Throwable) {
        project.logger.warn("${project.displayName}: fallback to use project-specific 'config/detekt-baseline.xml'")
        File("${project.projectDir}/config/detekt-baseline.xml")
    }
