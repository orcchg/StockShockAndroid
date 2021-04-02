package com.orcchg.yandexcontest.lint.rules

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.util.PsiUtil
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.USimpleNameReferenceExpression
import org.jetbrains.uast.visitor.UastVisitor

class ViewBindingOnDestroyViewUsageDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "ViewBindingOnDestroyViewUsageDetector",
            briefDescription = "Don't use view binding in onDestroyView",
            explanation = "Use onDestroyBinding instead",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                ViewBindingOnDestroyViewUsageDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )

        const val METHOD_NAME = "onDestroyView"
        const val VIEW_BINDING_CLASS_NAME = "androidx.viewbinding.ViewBinding"
        const val VIEW_BINDABLE_FRAGMENT_NAME = "ru.sberbank.sa.mobile.lib.core_ui.view.ViewBindableFragment"
    }

    override fun applicableSuperClasses(): List<String>? = listOf(VIEW_BINDABLE_FRAGMENT_NAME)

    override fun visitClass(context: JavaContext, declaration: UClass) {
        val method = declaration.uastDeclarations
            .asSequence()
            .filterIsInstance<UMethod>()
            .firstOrNull { it.name == METHOD_NAME }
            ?: return

        val evaluator = context.evaluator

        method.accept(object : UastVisitor {
            override fun visitElement(node: UElement): Boolean {
                if (node !is USimpleNameReferenceExpression) return false

                val expressionType = node.getExpressionType()
                val clazz = PsiUtil.resolveClassInType(expressionType)
                val extendsViewBinding = evaluator.extendsClass(
                    clazz,
                    VIEW_BINDING_CLASS_NAME,
                    true
                )
                if (extendsViewBinding) {
                    context.report(
                        issue = ISSUE,
                        location = context.getLocation(node),
                        message = "Avoid referencing ViewBinding in onDestroyView. Use onDestroyBinding instead"
                    )
                    return true
                }
                return false
            }
        })
    }
}
