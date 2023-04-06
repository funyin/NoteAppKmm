package com.initbase.noteappkmm.domain.time

import kotlinx.datetime.LocalDateTime
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

actual val dateTimeUTil: DateTimeUtil
    get() = DateTimeUtilImpl()

class DateTimeUtilImpl : DateTimeUtil() {
    override fun formatNoteDate(dateTime: LocalDateTime, format: String): String =
        SimpleDateFormat(format, Locale.getDefault()).format(toEpochMills(dateTime))

}