package com.initbase.noteappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.initbase.noteappkmm.domain.NoteDataSource
import com.initbase.noteappkmm.domain.note.Note
import com.initbase.noteappkmm.domain.time.dateTimeUTil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val noteTitleTextFocused = savedStateHandle.getStateFlow("noteTitleFocused", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val noteContentFocused = savedStateHandle.getStateFlow("noteContentFocused", false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor", Note.generateRandomColor())


    private val cleanState
        get() = NoteDetailState()
    val state = combine(
        noteTitle,
        noteTitleTextFocused,
        noteContent,
        noteContentFocused,
        noteColor
    ) { noteTitle,
        noteTitleTextFocused,
        noteContent,
        noteContentFocused,
        noteColor ->
        NoteDetailState(
            noteTitle, isNoteTitleHintVisible = !noteTitleTextFocused && noteTitle.isBlank(),
            noteContent = noteContent,
            isNoteContentHintVisible = !noteContentFocused && noteContent.isBlank(),
            noteColor = noteColor
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), cleanState)

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { noteId ->
            if (noteId == -1L) {
                return@let
            }
            existingNoteId = noteId
            viewModelScope.launch {
                noteDataSource.getNoteById(noteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }
            }
        }
    }


    fun onNoteTitleChanged(text: String) {
        savedStateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle["noteContent"] = text
    }

    fun onNoteTitleFocusedChanged(focused: Boolean) {
        savedStateHandle["noteTitleFocused"] = focused
    }

    fun onNoteContentFocusedChanged(focused: Boolean) {
        savedStateHandle["noteContentFocused"] = focused
    }

    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = dateTimeUTil.now()
                )
            )
            _hasNoteBeenSaved.update { true }
        }
    }
}