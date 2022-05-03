package com.orcchg.stockshock.infra.plugins.buildscript.staticanalysis

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class DependenciesAlphabeticalOrderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.register<VerifyAlphabeticalOrderForDependenciesTask>(TASK_NAME)
    }

    companion object {
        const val TASK_NAME = "verifyAlphabeticalOrderForDependencies"
    }
}
