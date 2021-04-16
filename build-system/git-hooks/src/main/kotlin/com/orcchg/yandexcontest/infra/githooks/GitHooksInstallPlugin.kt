package com.orcchg.yandexcontest.infra.githooks

import org.gradle.api.Plugin
import org.gradle.api.Project

class GitHooksInstallPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project.tasks) {
            register(TASK_GIT_HOOKS_PLUGIN_CHECK, GitHooksInstallPluginCheckTask::class.java)
            register(TASK_GIT_HOOKS_COPY, GitHooksCopyTask::class.java) {
                onlyIf { isUnixOs() }
                setDependsOn(listOf(TASK_GIT_HOOKS_PLUGIN_CHECK))
            }
            register(TASK_GIT_HOOKS_INSTALL, GitHooksInstallTask::class.java) {
                onlyIf { isUnixOs() }
                setDependsOn(listOf(TASK_GIT_HOOKS_COPY))
            }
        }
    }
}
