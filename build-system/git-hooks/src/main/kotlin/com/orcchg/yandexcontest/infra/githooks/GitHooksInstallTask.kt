package com.orcchg.yandexcontest.infra.githooks

import org.gradle.api.tasks.Exec

internal abstract class GitHooksInstallTask : Exec() {

    init {
        description = "Installs the pre-commit git hooks from scripts/git"
        group = "git hooks"
        workingDir = rootDir()
        commandLine = listOf("chmod", "-R", "+x", pathToGitHooks())
    }
}
