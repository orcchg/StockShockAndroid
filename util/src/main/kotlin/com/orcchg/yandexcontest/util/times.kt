package com.orcchg.yandexcontest.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun today(): LocalDate = LocalDate.now()

fun yesterday(): LocalDate = today().minusDays(1)

fun todayStart(): LocalDateTime =
    LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT)

/**
 * https://stackoverflow.com/questions/522251/whats-the-difference-between-iso-8601-and-rfc-3339-date-formats
 * https://medium.com/easyread/understanding-about-rfc-3339-for-datetime-formatting-in-software-engineering-940aa5d5f68a
 */
fun LocalDateTime.toRFC3339(): String =
    format(DateTimeFormatter.ISO_DATE_TIME)

fun LocalDate.`toYYYY-MM-DD`(): String =
    format(DateTimeFormatter.ISO_DATE)
