package com.orcchg.yandexcontest.infra.githooks

import org.gradle.api.Task
import java.util.Locale

internal fun isUnixOs(): Boolean {
    val osName = System.getProperty("os.name").toLowerCase(Locale.ROOT)
    return osName.contains("linux") || osName.contains("mac")
}

internal fun Task.rootDir() = project.rootProject.rootDir
internal fun Task.pathToGitHooks() = "${rootDir()}/.git/hooks/"
internal fun Task.pathToGitScripts() = "${rootDir()}/scripts/git/"
internal fun Task.fileExists(path: String): Boolean = project.file(path).exists()

internal const val TASK_GIT_HOOKS_COPY = "gitHooksCopy"
internal const val TASK_GIT_HOOKS_INSTALL = "gitHooksInstall"
internal const val TASK_GIT_HOOKS_PLUGIN_CHECK = "gitHooksPluginCheck"
