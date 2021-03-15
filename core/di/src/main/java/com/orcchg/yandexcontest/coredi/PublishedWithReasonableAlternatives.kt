package com.orcchg.yandexcontest.coredi

import kotlin.reflect.KClass

/**
 * Denotes dagger modules that provide published [binding] with a reasonable alternatives.
 *
 * @details It is supposed that [binding] will have alternatives, normally
 *     for testing purposes (in terms of fake implementations). For each such
 *     kind of a binding there must be exactly one dagger module.
 *     Alternatives each gets it own module.
 *
 * @property binding Class of binding instance that will be provided by the module.
 *
 * @see https://dagger.dev/dev-guide/testing.html
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class PublishedWithReasonableAlternatives(
    val binding: KClass<*>
)
