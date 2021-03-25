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

class HardcodedHexColorXmlDetector : ResourceXmlDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.LAYOUT

    override fun getApplicableAttributes(): Collection<String>? = listOf("background")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val attributeValue = attribute.nodeValue

        if (!attributeValue.startsWith("#")) {
            return
        }

        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "Hardcoded hex colors should be declared in a `<color>` resource."
        )
    }

    companion object {
        @JvmStatic
        val ISSUE = Issue.create(
            id = "HardcodedHexColorXml",
            briefDescription = "Prohibits hardcoded hex colors in layout XML",
            explanation = "Hardcoded hex colors should be declared in a `<color>` resource",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedHexColorXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}