package com.initbase.noteappkmm.android.note_list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.initbase.noteappkmm.domain.NoteDataSource
import com.initbase.noteappkmm.domain.note.Note
import com.initbase.noteappkmm.domain.note.SearchNotes
import com.initbase.noteappkmm.domain.time.dateTimeUTil
import com.initbase.noteappkmm.presentation.RedOrangeHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    init {
        viewModelScope.launch {
            /*repeat((1..10).count()) {
                noteDataSource.insertNote(
                    Note(
                        id = null,
                        title = "Note $it",
                        content = "Sample Content $it",
                        colorHex = RedOrangeHex,
                        created = dateTimeUTil.now()
                    )
                )
            }*/
        }
    }

    private val searchUseCase = SearchNotes()

    private val notes = savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)


    val state = combine(notes, searchText, isSearchActive) { notes, query, isSearchActive ->
        NoteListState(
            notes = searchUseCase.execute(notes, query),
            searchText = query,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())


    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }


    fun onSearchTextChanged(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        val isSearchActive = !isSearchActive.value
        savedStateHandle["isSearchActive"] = isSearchActive
        if (isSearchActive)
            savedStateHandle["searchText"] = ""
    }

    fun deleteNoteById(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }
}