package com.orcchg.stockshock.staticanalysis.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtImportDirective

class SampleRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue = Issue(
        id = "SampleRule",
        severity = Severity.Style,
        debt = Debt(mins = 1),
        description = "Sample detekt rule"
    )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (importDirective.text.contains("io.reactivex.schedulers.Schedulers")) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(importDirective),
                    message = "Get rid of sample import usage"
                )
            )
        }
    }
}
