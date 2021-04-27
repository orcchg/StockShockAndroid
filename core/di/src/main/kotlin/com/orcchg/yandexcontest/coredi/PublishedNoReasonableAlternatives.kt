package com.orcchg.yandexcontest.coredi

/**
 * Denotes dagger modules that provide published binding with no any reasonable alternatives.
 *
 * @see https://dagger.dev/dev-guide/testing.html
 */
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class PublishedNoReasonableAlternatives
