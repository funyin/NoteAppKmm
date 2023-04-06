//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Funyinoluwa Kashimawo on 02/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    private var dataSource: NoteDataSource
    @StateObject var viewModel = NoteViewModel(noteDataSource:nil)
    
    @State private var isNoteSelected = false
    @State private var selectedNoteId:Int64? = nil
    
    init(dataSourcse: NoteDataSource) {
        self.dataSource = dataSourcse
    }
    var body: some View {
        VStack{
            ZStack{
                NavigationLink(destination: NoteDetailScreen(noteDataSource: self.dataSource,noteId: selectedNoteId), isActive: $isNoteSelected){
                    EmptyView()
                }.hidden()
                HideableSearchTextField(onSearchToggled: {
                    viewModel.toggleIsSearchActive()
                }, destinationProvider: {
                    NoteDetailScreen(noteDataSource: dataSource)
                }, isSearchActive: viewModel.isSearchActive, searchText: $viewModel.searchText)
                .frame(maxWidth:.infinity,minHeight: 40)
                .padding()
                if !viewModel.isSearchActive {
                    Text("All notes").font(.title2)
                }
            }
            List{
                ForEach(viewModel.filteredNotes, id: \.self.id, content: {note in
                    Button(action: {
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                    }){
                        NoteItem(note: note, onDeleteClick: {
                            viewModel.deleteNoteById(id: note.id?.int64Value)})
                    }
                })
            }.onAppear{
                viewModel.loadNotes()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)
        }.onAppear{
            viewModel.setNoteDataSource(noteDataSource: dataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
