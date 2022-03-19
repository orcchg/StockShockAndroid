package com.orcchg.stockshock.staticanalysis.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtImportDirective

class ForbidDefaultRxSchedulersRule(config: Config = Config.empty) : Rule(config) {

    override val issue: Issue =
        Issue(
            id = "ForbidDefaultRxSchedulers",
            severity = Severity.Defect,
            debt = Debt(mins = 1),
            description = "Using of standard [io.reactivex.schedulers.Schedulers] is forbidden, " +
                "you must only use [com.talabat.core.dispatcher.domain.RxSchedulersFactory]."
        )

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (
            importDirective.text.contains("io.reactivex.schedulers.Schedulers") ||
            importDirective.text.contains("io.reactivex.internal.schedulers.IoScheduler") ||
            importDirective.text.contains("io.reactivex.android.schedulers.AndroidSchedulers")
        ) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(importDirective),
                    message = "Remove all usages of [io.reactivex.schedulers.Schedulers / IoScheduler / AndroidSchedulers]"
                )
            )
        }
    }
}
