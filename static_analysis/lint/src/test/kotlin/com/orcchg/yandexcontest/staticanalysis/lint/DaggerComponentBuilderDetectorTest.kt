package com.orcchg.yandexcontest.staticanalysis.lint

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.java
import com.android.tools.lint.checks.infrastructure.TestFiles.kt
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.orcchg.yandexcontest.staticanalysis.lint.rules.DaggerComponentBuilderDetector
import org.junit.Test

class DaggerComponentBuilderDetectorTest {

    @Test fun `should not report Component Factory`() {
        lint()
            .files(DAGGER_COMPONENT, COMPONENT_FACTORY_FILE)
            .issues(DaggerComponentBuilderDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test fun `should warning about Component Builder`() {
        lint()
            .files(DAGGER_COMPONENT, COMPONENT_BUILDER_FILE)
            .issues(DaggerComponentBuilderDetector.ISSUE)
            .run()
            .expect("""
                src/ru/sberbank/sa/mobile/feature/deliveryclub/proxy/impl/di/],.kt:14: Error: Usages of Dagger Component.Builder are prohibited, use Component.Factory instead [DaggerComponentBuilder]
                    @Component.Builder
                    ~~~~~~~~~~~~~~~~~~
                1 errors, 0 warnings
            """.trimIndent())
    }
}

val COMPONENT_BUILDER_FILE = kt("""
    package ru.sberbank.sa.mobile.feature.deliveryclub.proxy.impl.di

    import dagger.Component
    import ru.sberbank.sa.mobile.feature.deliveryclub.proxy.impl.presentation.viewmodel.DeliveryClubProxyViewModel

    @Component(
        dependencies = [DeliveryClubProxyFeatureDependencies::class],
        modules = [DeliveryClubProxyFeatureModule::class]
    )
    interface DeliveryClubProxyFeatureComponent : DeliveryClubProxyFeatureInternalApi {

        override fun deliveryClubProxyViewModel(): DeliveryClubProxyViewModel

        @Component.Builder
        interface Builder {

            fun dependencies(dependencies: DeliveryClubProxyFeatureDependencies): Builder
            fun build(): DeliveryClubProxyFeatureComponent
        }

        @Component
        interface DeliveryClubProxyFeatureDependenciesComponent : DeliveryClubProxyFeatureDependencies
    }
""").indented()

val COMPONENT_FACTORY_FILE = kt("""
    package ru.sberbank.sa.mobile.feature.deliveryclub.proxy.impl.di

    import dagger.Component
    import ru.sberbank.sa.mobile.feature.deliveryclub.proxy.impl.presentation.viewmodel.DeliveryClubProxyViewModel

    @Component(
        dependencies = [DeliveryClubProxyFeatureDependencies::class],
        modules = [DeliveryClubProxyFeatureModule::class]
    )
    interface DeliveryClubProxyFeatureComponent : DeliveryClubProxyFeatureInternalApi {

        override fun deliveryClubProxyViewModel(): DeliveryClubProxyViewModel

        @Component.Factory
        interface Factory {

            fun create(dependencies: DeliveryClubProxyFeatureDependencies): DeliveryClubProxyFeatureComponent
        }

        @Component
        interface DeliveryClubProxyFeatureDependenciesComponent : DeliveryClubProxyFeatureDependencies
    }
""").indented()

val DAGGER_COMPONENT: TestFile = java("""
    package dagger;
    
    public @interface Component {

        @interface Builder {}
        @interface Factory {}
    }
""").indented()