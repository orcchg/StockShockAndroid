package com.orcchg.yandexcontest.lint.rules

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class NavControllerNavigateDeeplinkDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "NavControllerNavigateDeeplink",
            briefDescription = "Prohibits usages of `androidx.navigation.NavController.navigate(android.net.Uri)`",
            explanation = "Usages of `androidx.navigation.NavController.navigate(android.net.Uri)` are prohibited",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                NavControllerNavigateDeeplinkDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableMethodNames(): List<String> = listOf("navigate")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "androidx.navigation.NavController")) {
            return
        }

        val first = "android.net.Uri"
        val second = "androidx.navigation.NavOptions"
        val third = "androidx.navigation.Navigator.Extras"
        if (!context.evaluator.parametersMatch(method, first) &&
            !context.evaluator.parametersMatch(method, first, second) &&
            !context.evaluator.parametersMatch(method, first, second, third)
        ) {
            return
        }

        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getLocation(node),
            message = "Use an extension function instead."
        )
    }
}