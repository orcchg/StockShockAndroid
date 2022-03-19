package com.orcchg.stockshock.staticanalysis.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtImportDirective

class ForbidDefaultCoroutineDispatchersRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue =
        Issue(
            id = "ForbidDefaultCoroutineDispatchers",
            severity = Severity.Defect,
            debt = Debt(mins = 1),
            description = "Using of standard [kotlinx.coroutines.Dispatchers] is forbidden, " +
                "you must only use [com.talabat.core.dispatcher.domain.CoroutineDispatchersFactory]."
        )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (importDirective.text.contains("kotlinx.coroutines.Dispatchers")) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(importDirective),
                    message = "Remove all usages of [kotlinx.coroutines.Dispatchers]"
                )
            )
        }
    }
}
