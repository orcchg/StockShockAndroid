package com.orcchg.yandexcontest.infra.githooks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.TaskAction

internal abstract class GitHooksInstallPluginCheckTask : DefaultTask() {

    init {
        description = "Verifies that plugin could operate properly in this environment"
    }

    @TaskAction
    fun checkGitHooksInstallPluginOperatible() {
        val pathToGitHooks = pathToGitHooks()
        if (!project.file(pathToGitHooks).exists()) {
            throw StopExecutionException("Path not exists: $pathToGitHooks")
        }
        val pathToGitScripts = pathToGitScripts()
        if (!project.file(pathToGitScripts).exists()) {
            throw StopExecutionException("Path not exists: $pathToGitScripts")
        }
        println("Plugin git-hooks-install can operate properly!")
    }
}
