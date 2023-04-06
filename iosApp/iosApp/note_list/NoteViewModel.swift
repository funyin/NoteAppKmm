//
//  NoteViewModel.swift
//  iosApp
//
//  Created by Funyinoluwa Kashimawo on 02/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen {
    @MainActor class NoteViewModel: ObservableObject {
        private var noteDataSource: NoteDataSource? = nil
        
        private var searchNotes = SearchNotes()
        private var notes = [Note]()
        @Published private(set) var filteredNotes = [Note]()
        @Published var searchText = "" {
            didSet{
                filteredNotes = searchNotes.execute(notes: notes, query: searchText)
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func setNoteDataSource(noteDataSource: NoteDataSource){
            self.noteDataSource = noteDataSource
        }
        
        func deleteNoteById(id: Int64?){
            if id != nil{
                noteDataSource?.deleteNoteById(id: id!, completionHandler: { error in
                    self.loadNotes()
                })
            }
        }
        
        func toggleIsSearchActive(){
            isSearchActive = !isSearchActive
            if !isSearchActive {
                searchText  = ""
            }
        }
        
        func loadNotes(){
            noteDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ??  []
                self.filteredNotes = self.notes
            })
        }
    }
}
