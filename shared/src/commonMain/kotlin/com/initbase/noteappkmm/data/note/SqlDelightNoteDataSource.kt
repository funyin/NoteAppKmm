package com.initbase.noteappkmm.data.note

import com.initbase.noteappkmm.database.NoteDatabase
import com.initbase.noteappkmm.domain.NoteDataSource
import com.initbase.noteappkmm.domain.note.Note
import com.initbase.noteappkmm.domain.note.toNote
import com.initbase.noteappkmm.domain.time.dateTimeUTil
import database.NoteQueries

class SqlDelightNoteDataSource(db: NoteDatabase) : NoteDataSource {
    private val queries: NoteQueries = db.noteQueries
    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = dateTimeUTil.toEpochMills(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes().executeAsList().map { it.toNote() }
    }

    override suspend fun deleteNoteById(id: Long) {
        return queries.deleteNote(id)
    }
}