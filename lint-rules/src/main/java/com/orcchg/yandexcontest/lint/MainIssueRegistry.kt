package com.orcchg.yandexcontest.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.lint.rules.AlertDialogUsageDetector
import com.orcchg.yandexcontest.lint.rules.DaggerComponentBuilderDetector
import com.orcchg.yandexcontest.lint.rules.HardcodedHexColorXmlDetector
import com.orcchg.yandexcontest.lint.rules.HardcodedTextStyleSizeDetector
import com.orcchg.yandexcontest.lint.rules.InterfaceNamingDetector
import com.orcchg.yandexcontest.lint.rules.NavControllerNavigateDeeplinkDetector
import com.orcchg.yandexcontest.lint.rules.PlatformStylesDetector
import com.orcchg.yandexcontest.lint.rules.TintColorDrawableDetector
import com.orcchg.yandexcontest.lint.rules.VectorDrawableFillColorDetector
import com.orcchg.yandexcontest.lint.rules.VectorDrawableFillTypeDetector
import com.orcchg.yandexcontest.lint.rules.VectorDrawableSizeDetector
import com.orcchg.yandexcontest.lint.rules.VectorDrawableTintColorDetector
import com.orcchg.yandexcontest.lint.rules.ViewBindingOnDestroyViewUsageDetector

class MainIssueRegistry : IssueRegistry() {

    override val api: Int = CURRENT_API

    override val issues: List<Issue>
        get() = listOf(
            InterfaceNamingDetector.ISSUE,
            HardcodedHexColorXmlDetector.ISSUE,
            TintColorDrawableDetector.ISSUE,
            AlertDialogUsageDetector.ISSUE,
            HardcodedTextStyleSizeDetector.ISSUE,
            VectorDrawableSizeDetector.ISSUE,
            VectorDrawableFillTypeDetector.ISSUE,
            VectorDrawableFillColorDetector.ISSUE,
            VectorDrawableTintColorDetector.ISSUE,
            PlatformStylesDetector.ISSUE,
            DaggerComponentBuilderDetector.ISSUE,
            NavControllerNavigateDeeplinkDetector.ISSUE,
            ViewBindingOnDestroyViewUsageDetector.ISSUE
        )
}
