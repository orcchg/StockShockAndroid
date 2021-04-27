package com.orcchg.yandexcontest.staticanalysis.lint.rules

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

class HardcodedTextStyleSizeDetector : ResourceXmlDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.LAYOUT

    override fun getApplicableAttributes(): Collection<String>? = listOf("textSize", "textStyle")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "Use textAppearance instead"
        )
    }

    companion object {
        @JvmStatic
        val ISSUE = Issue.create(
            id = "HardcodedTextSizeDetector",
            briefDescription = "Prohibits hardcoded textSize and textStyle in layout XML",
            explanation = "Use textAppearance instead",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedTextStyleSizeDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}
