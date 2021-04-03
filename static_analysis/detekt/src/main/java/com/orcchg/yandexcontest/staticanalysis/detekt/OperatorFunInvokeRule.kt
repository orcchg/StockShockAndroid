package com.orcchg.yandexcontest.staticanalysis.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.rules.isOperator
import org.jetbrains.kotlin.psi.KtNamedFunction

class OperatorFunInvokeRule(config: Config = Config.empty) : Rule(config) {
    override val issue: Issue = Issue(
        id = "OperatorFunInvoke",
        severity = Severity.Style,
        debt = Debt(mins = 1),
        description = "Don't use 'operator fun invoke()', because implicit call of 'invoke()' is not permitted"
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        if (function.isOperator() && function.name == "invoke") {
            report(CodeSmell(
                issue = issue,
                entity = Entity.atName(function),
                message = "Get rid 'operator' modifier"
            ))
        }
    }
}
