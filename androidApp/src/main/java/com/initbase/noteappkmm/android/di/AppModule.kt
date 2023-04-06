package com.initbase.noteappkmm.android.di

import android.app.Application
import com.initbase.noteappkmm.data.local.DatabaseDriverFactory
import com.initbase.noteappkmm.data.note.SqlDelightNoteDataSource
import com.initbase.noteappkmm.database.NoteDatabase
import com.initbase.noteappkmm.domain.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun providesNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }
}