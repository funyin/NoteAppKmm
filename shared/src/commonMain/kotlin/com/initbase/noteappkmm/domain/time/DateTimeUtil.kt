package com.initbase.noteappkmm.domain.time

import kotlinx.datetime.*

abstract class DateTimeUtil {

    fun now(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    fun toEpochMills(dateTime: LocalDateTime): Long =
        dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

    abstract fun formatNoteDate(dateTime: LocalDateTime, format: String): String

    fun fromEpochMills(milliseconds: Long): LocalDateTime =
        Instant.fromEpochMilliseconds(milliseconds).toLocalDateTime(TimeZone.currentSystemDefault())
}

expect val dateTimeUTil: DateTimeUtil