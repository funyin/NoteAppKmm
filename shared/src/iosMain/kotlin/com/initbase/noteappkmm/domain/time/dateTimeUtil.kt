package com.initbase.noteappkmm.domain.time

import kotlinx.datetime.LocalDateTime

actual val dateTimeUTil: DateTimeUtil
    get() = DateTimeUtilImpl()

class DateTimeUtilImpl : DateTimeUtil() {

    override fun formatNoteDate(dateTime: LocalDateTime, format: String): String {
        return "${dateTime.dayOfWeek.name}, ${dateTime.month.name} ${dateTime.year}. ${dateTime.time.hour}:${dateTime.time.minute}"
    }
}
