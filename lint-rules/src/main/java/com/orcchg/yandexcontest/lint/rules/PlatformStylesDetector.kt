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

class PlatformStylesDetector : ResourceXmlDetector() {

    private val regex = "@style/\\w+(\\.MaterialComponents\\.|\\.AppCompat\\.|\\.Design\\.).+".toRegex()

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.LAYOUT

    override fun getApplicableAttributes(): Collection<String>? = listOf("style", "theme")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val attributeValue = attribute.nodeValue
        if (!attributeValue.matches(regex)) return

        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "Use *.SuperApp.* styles"
        )
    }

    companion object {
        @JvmStatic
        val ISSUE = Issue.create(
            id = "PlatformStylesDetector",
            briefDescription = "Prohibits platform styles in layout XML",
            explanation = "Use *.SuperApp.* styles",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                PlatformStylesDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}