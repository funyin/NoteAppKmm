package com.initbase.noteappkmm.domain.note

import com.initbase.noteappkmm.domain.time.dateTimeUTil

class SearchNotes {

    fun execute(notes: List<Note>, query: String): List<Note> {
        val sortSelector: (Note) -> Long? = { dateTimeUTil.toEpochMills(it.created) }
        if (query.isBlank())
            return notes.sortedByDescending(sortSelector)
        return notes.filter {
            val mQuery = query.lowercase()
            it.title.trim().lowercase().contains(mQuery) || it.content.trim().lowercase()
                .contains(mQuery)
        }.sortedByDescending(sortSelector)
    }
}