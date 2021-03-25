package com.orcchg.yandexcontest.lint.rules

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

class TintColorDrawableDetector : ResourceXmlDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.DRAWABLE

    override fun getApplicableAttributes(): Collection<String>? = listOf("tint")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val attributeValue = attribute.nodeValue

        if (!attributeValue.startsWith("@") && !attributeValue.startsWith("#")) {
            return
        }

        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "Colors should be referenced as theme attributes"
        )
    }

    companion object {
        @JvmStatic
        val ISSUE = Issue.create(
            id = "TintColorDrawableDetector",
            briefDescription = "Prohibits color references in drawables",
            explanation = "Colors should be referenced as theme attributes",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                TintColorDrawableDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}