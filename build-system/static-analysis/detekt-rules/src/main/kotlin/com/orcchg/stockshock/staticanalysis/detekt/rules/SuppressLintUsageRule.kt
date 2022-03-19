package com.orcchg.stockshock.staticanalysis.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtImportDirective

class SuppressLintUsageRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue = Issue(
        id = "SuppressLintUsage",
        severity = Severity.Defect,
        debt = Debt(mins = 1),
        description = "Annotation [@SuppressLint] must be replaced with [@kotlin.Suppress]."
    )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (importDirective.text.contains("android.annotation.SuppressLint")) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(importDirective),
                    message = "Replace all occurrences of [@android.annotation.SuppressLint] for [@kotlin.Suppress] " +
                        "and remove android-* dependencies and android-* plugins in build.gradle(.kts) files, " +
                        "if they would not need them any more, after fixing aforementioned annotations."
                )
            )
        }
    }
}
