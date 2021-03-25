package com.orcchg.yandexcontest.lint.rules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UElement

class DaggerComponentBuilderDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes() = listOf<Class<out UElement>>(UAnnotation::class.java)

    override fun createUastHandler(context: JavaContext) = AnnotationVisitor(context)

    class AnnotationVisitor(private val context: JavaContext) : UElementHandler() {

        override fun visitAnnotation(node: UAnnotation) {
            val qn = node.qualifiedName
            if (qn != null && qn == "dagger.Component.Builder") {
                context.report(ISSUE, node, context.getNameLocation(node), "Usages of Dagger Component.Builder are prohibited, use Component.Factory instead")
            }
        }
    }

    companion object {
        @JvmStatic
        val ISSUE = Issue.create(
            id = "DaggerComponentBuilder",
            briefDescription = "Prohibits usages of Dagger Component.Builder",
            explanation = "Usages of Dagger Component.Builder are prohibited, use Component.Factory instead",
            category = Category.PERFORMANCE,
            severity = Severity.ERROR,
            implementation = Implementation(
                DaggerComponentBuilderDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}