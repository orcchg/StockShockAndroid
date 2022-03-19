package com.orcchg.stockshock.staticanalysis.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.rules.isOperator
import org.jetbrains.kotlin.psi.KtNamedFunction

private const val INVOKE_FUN_NAME = "invoke"

class OperatorFunInvokeRule(config: Config = Config.empty) : Rule(config) {
    override val issue: Issue = Issue(
        id = "OperatorFunInvoke",
        severity = Severity.Style,
        debt = Debt(mins = 5),
        description = "Don't use [operator fun invoke()], because implicit invoke() " +
            "is forbidden by the code style conventions."
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        if (function.isOperator() && function.name == INVOKE_FUN_NAME) {
            report(CodeSmell(
                issue = issue,
                entity = Entity.atName(function),
                message = "Remove [operator] modifier"
            ))
        }
    }
}
