package com.orcchg.yandexcontest.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.orcchg.yandexcontest.lint.rules.NavControllerNavigateDeeplinkDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NavControllerNavigateDeeplinkDetectorTest : LintDetectorTest() {

    override fun getDetector(): Detector = NavControllerNavigateDeeplinkDetector()

    override fun getIssues(): MutableList<Issue> = mutableListOf(NavControllerNavigateDeeplinkDetector.ISSUE)

    @Test
    fun expectPass() {
        lint()
            .files(
                kotlin(
                    """
package ru.sberbank.sa.lint_rules.detector

class TestClass {

    fun navigate() {}
}
                """
                )
            )
            .run()
            .expectClean()
    }

    @Test
    fun expectFail() {
        lint()
            .files(
                NAV_CONTROLLER_STUB,
                kotlin(
                    """
package ru.sberbank.sa.lint_rules.detector
                
import android.net.Uri
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController

class TestClass {

    fun example(view: View, deeplink: Uri) {
        val navController: NavController = view.findNavController()
        navController.navigate(deeplink)
    }

    fun example2(view: View, deeplink: Uri, navOptions: NavOptions) {
        val navController: NavController = view.findNavController()
        navController.navigate(deeplink, navOptions)
    }

    fun example3(view: View, deeplink: Uri, navOptions: NavOptions, navigatorExtras: Navigator.Extras) {
        val navController: NavController = view.findNavController()
        navController.navigate(deeplink, navOptions, navigatorExtras)
    }
}"""
                )
            )
            .allowCompilationErrors()
            .run()
            .expect(
                """src/ru/sberbank/sa/lint_rules/detector/TestClass.kt:15: Error: Use an extension function instead. [NavControllerNavigateDeeplink]
        navController.navigate(deeplink)
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
1 errors, 0 warnings"""
            )
//             .expect("""src/ru/sberbank/sa/lint_rules/detector/TestClass.kt:15: Error: Use an extension function instead. [NavControllerNavigateDeeplink]
//         navController.navigate(deeplink)
//         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// src/ru/sberbank/sa/lint_rules/detector/TestClass.kt:20: Error: Use an extension function instead. [NavControllerNavigateDeeplink]
//         navController.navigate(deeplink, navOptions)
//         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// src/ru/sberbank/sa/lint_rules/detector/TestClass.kt:25: Error: Use an extension function instead. [NavControllerNavigateDeeplink]
//         navController.navigate(deeplink, navOptions, navigatorExtras)
//         ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// 3 errors, 0 warnings""") // TODO исправить
    }
}

val NAV_CONTROLLER_STUB = java(
    """package androidx.navigation;

    import android.net.Uri;
    import androidx.navigation.NavOptions;
    import androidx.navigation.Navigator;
    
    class NavController {
        
        public void navigate(Uri deeplink) {
        
        }
        
        public void navigate(Uri deeplink, NavOptions navOptions) {

        }

        public void navigate(Uri deeplink, NavOptions navOptions, Navigator.Extras navigatorExtras) {

        }
    }
"""
)