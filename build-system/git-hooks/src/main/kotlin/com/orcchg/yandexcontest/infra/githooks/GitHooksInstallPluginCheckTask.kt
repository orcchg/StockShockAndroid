package com.orcchg.yandexcontest.infra.githooks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.TaskAction

internal abstract class GitHooksInstallPluginCheckTask : DefaultTask() {

    init {
        description = "Verifies that plugin could operate properly in this environment"
        group = "git hooks"
    }

    @TaskAction
    fun checkGitHooksInstallPluginOperatible() {
        val pathToGitHooks = pathToGitHooks()
        if (!fileExists(pathToGitHooks)) {
            // if .git directory is absent we don't need to install git hooks as well
            throw StopExecutionException("Path not exists: $pathToGitHooks")
        }
        val pathToGitScripts = pathToGitScripts()
        if (!fileExists(pathToGitScripts)) {
            // if git scripts directory is absent - something wrong with integrity of the local repository
            throw IllegalStateException("Path not exists: $pathToGitScripts")
        }
    }
}
