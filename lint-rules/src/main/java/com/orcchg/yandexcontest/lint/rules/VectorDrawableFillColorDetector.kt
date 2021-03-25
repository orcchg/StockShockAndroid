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

class VectorDrawableFillColorDetector : ResourceXmlDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.DRAWABLE

    override fun getApplicableAttributes(): Collection<String>? = listOf("fillColor")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val rootTagName = XmlUtils.getRootTagName(context.file)
        if (rootTagName != TAG_VECTOR && rootTagName != TAG_ANIMATED_VECTOR) return

        val attributeValue = attribute.nodeValue

        if ((attributeValue.startsWith("#") && attributeValue.length > 7) || attributeValue.startsWith("?")) {
            context.report(
                issue = ISSUE,
                scope = attribute,
                location = context.getValueLocation(attribute),
                message = """FillColor should be a solid color without opacity, preferably #000000.
                    |For tinting use android:tint on a <vector> element or a view""".trimMargin()
            )
        }
    }

    companion object {
        const val TAG_ANIMATED_VECTOR = "animated-vector"
        const val TAG_VECTOR = "vector"

        @JvmStatic
        val ISSUE = Issue.create(
            id = "VectorDrawableFillColorDetector",
            briefDescription = "Prohibits alpha channel in fillColor",
            explanation = "fillColor should be a solid color without opacity",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                VectorDrawableFillColorDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}