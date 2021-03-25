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

class VectorDrawableFillTypeDetector : ResourceXmlDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.DRAWABLE

    override fun getApplicableAttributes(): Collection<String>? = listOf("fillType")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        if (context.folderVersion >= 24) return
        val rootTagName = XmlUtils.getRootTagName(context.file)
        if (rootTagName != TAG_VECTOR && rootTagName != TAG_ANIMATED_VECTOR) return

        context.report(
            issue = ISSUE,
            scope = attribute,
            location = context.getValueLocation(attribute),
            message = "fillType shouldn't be used in VectorDrawables on api less than 24"
        )
    }

    companion object {
        const val TAG_ANIMATED_VECTOR = "animated-vector"
        const val TAG_VECTOR = "vector"

        @JvmStatic
        val ISSUE = Issue.create(
            id = "VectorDrawableFillTypeDetector",
            briefDescription = "Prohibits the usage of fillType in VectorDrawables",
            explanation = "fillType shouldn't be used in VectorDrawables on api less than 24",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                VectorDrawableFillTypeDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}