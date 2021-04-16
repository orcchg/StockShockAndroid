package com.orcchg.yandexcontest.infra.githooks

import org.gradle.api.tasks.Copy

internal abstract class GitHooksCopyTask : Copy() {

    init {
        description = "Copies the git hooks from scripts/git to the .git folder"
        group = "git hooks"

        from(pathToGitScripts()) {
            include("**/*.sh")
            rename("(.*).sh", "$1")
        }
        into(pathToGitHooks())
    }
}
