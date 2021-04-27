package com.orcchg.yandexcontest.staticanalysis.lint.rules

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

class VectorDrawableSizeDetector : ResourceXmlDetector() {

    private val regex = "[^0-9]".toRegex()

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.DRAWABLE

    override fun getApplicableAttributes(): Collection<String>? = listOf(
        "width",
        "height",
        "viewportWidth",
        "viewportHeight"
    )

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val rootTagName = XmlUtils.getRootTagName(context.file)
        if (rootTagName != TAG_VECTOR && rootTagName != TAG_ANIMATED_VECTOR) return

        val attributeValue = attribute.nodeValue.replace(regex, "").toIntOrNull() ?: return

        if (attributeValue >= 24) return

        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "VectorDrawable size should be more than or equal to 24dp"
        )
    }

    companion object {
        const val TAG_ANIMATED_VECTOR = "animated-vector"
        const val TAG_VECTOR = "vector"

        @JvmStatic
        val ISSUE = Issue.create(
            id = "VectorDrawableSizeDetector",
            briefDescription = "Prohibits VectorDrawables less than 24dp",
            explanation = "VectorDrawable size should be more than or equal to 24dp",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                VectorDrawableSizeDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}
