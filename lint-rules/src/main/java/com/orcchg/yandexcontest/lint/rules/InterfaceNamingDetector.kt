package com.orcchg.yandexcontest.lint.rules

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UClass
import java.util.regex.Pattern

class InterfaceNamingDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext) = NamingPatternHandler(context)

    class NamingPatternHandler(private val context: JavaContext) : UElementHandler() {
        override fun visitClass(node: UClass) {
            if (node.isInterface && Pattern.matches(PATTERN, node.name)) {
                reportUsage(context, node)
            }
        }

        private fun reportUsage(context: JavaContext, clazz: UClass) {
            context.report(
                issue = ISSUE,
                scopeClass = clazz,
                location = context.getNameLocation(clazz),
                message = "Interface naming error"
            )
        }
    }

    companion object {
        private val IMPLEMENTATION = Implementation(
            InterfaceNamingDetector::class.java,
            Scope.JAVA_FILE_SCOPE
        )

        val ISSUE: Issue = Issue
            .create(
                id = "InterfaceNamingDetector",
                briefDescription = "Prefix I in interface name",
                explanation = "You should not use I prefix in interface name.".trimIndent(),
                category = Category.CORRECTNESS,
                priority = 9,
                severity = Severity.ERROR,
                androidSpecific = true,
                implementation = IMPLEMENTATION
            )
    }
}

private const val PATTERN = "I[A-Z]\\S+"
