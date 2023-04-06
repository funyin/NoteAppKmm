package com.initbase.noteappkmm.di

import com.initbase.noteappkmm.data.local.DatabaseDriverFactory
import com.initbase.noteappkmm.data.note.SqlDelightNoteDataSource
import com.initbase.noteappkmm.database.NoteDatabase
import com.initbase.noteappkmm.domain.NoteDataSource

class DatabaseModule {
    private val factory: DatabaseDriverFactory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy { SqlDelightNoteDataSource(NoteDatabase(factory.createDriver())) }
}