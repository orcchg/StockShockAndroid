package com.orcchg.yandexcontest.staticanalysis.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class CustomRulesProvider : RuleSetProvider {

    override val ruleSetId = "custom-rules"

    override fun instance(config: Config) =
        RuleSet(ruleSetId, listOf(OperatorFunInvokeRule(config)))
}
