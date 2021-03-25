package com.orcchg.yandexcontest.lint.rules

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import com.android.utils.XmlUtils
import org.w3c.dom.Attr

class VectorDrawableTintColorDetector : ResourceXmlDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.DRAWABLE

    override fun getApplicableAttributes(): Collection<String>? = listOf("tint")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val name = context.project.name
        if (name != "DesignSystemCoreLib") return

        val rootTagName = XmlUtils.getRootTagName(context.file)
        if (rootTagName != TAG_VECTOR && rootTagName != TAG_ANIMATED_VECTOR) return

        val attributeValue = attribute.nodeValue

        if (attributeValue != "?attr/colorOnBackgroundSecondary" && attributeValue != "?colorOnBackgroundSecondary") {
            context.report(
                issue = ISSUE,
                scope = attribute,
                location = context.getValueLocation(attribute),
                message = """Tint color should be equal to ?attr/colorOnBackgroundSecondary in design system icons.
                    |For different color use android:tint on a view""".trimMargin()
            )
        }
    }

    companion object {
        const val TAG_ANIMATED_VECTOR = "animated-vector"
        const val TAG_VECTOR = "vector"

        @JvmStatic
        val ISSUE = Issue.create(
            id = "VectorDrawableTintColorDetector",
            briefDescription = "Enforces colorOnBackgroundSecondary as default tint color in design system icons",
            explanation = "Tint color should be equal to ?attr/colorOnBackgroundSecondary in design system icons",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                VectorDrawableTintColorDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}