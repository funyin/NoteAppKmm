package com.initbase.noteappkmm.domain.note

import com.initbase.noteappkmm.domain.time.DateTimeUtil
import com.initbase.noteappkmm.domain.time.dateTimeUTil
import database.NoteEntity

fun NoteEntity.toNote():Note = Note(id = id,title,content,colorHex, dateTimeUTil.fromEpochMills(created))