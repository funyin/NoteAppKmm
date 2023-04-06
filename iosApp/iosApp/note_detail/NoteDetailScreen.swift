//
//  NoteDetailScreen.swift
//  iosApp
//
//  Created by Funyinoluwa Kashimawo on 06/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteDetailScreen: View {
    private var noteDataSource : NoteDataSource
    private var noteId : Int64? = nil
    
    @StateObject var viewModel = NoteDetaiViewModel(noteDataSource: nil)
    @Environment(\.presentationMode) var presentation
    
    init(noteDataSource: NoteDataSource, noteId: Int64? = nil) {
        self.noteDataSource = noteDataSource
        self.noteId = noteId
        UITextView.appearance().backgroundColor = .clear
    }
    
    var body: some View {
        let backgroundColor: Color = Color(hex: viewModel.noteColor)
        VStack(alignment: .leading){
            TextField("Enter a title...",text: $viewModel.noteTitle)
                .font(.title)
            ZStack(alignment: .topLeading){
                if viewModel.noteContent.isEmpty {
                    Text("Enter some content...")
                        .font(.title)
                        .foregroundColor(.gray)
                        .padding(EdgeInsets(top: 6, leading: 2, bottom: 0, trailing: 0))
                }
                TextEditor(text: $viewModel.noteContent)
                    .font(.title)
                    .scrollContentBackground(.hidden).offset(x:-4)
            }
            Spacer()
        }.toolbar(content: {
            Button(action: {
                viewModel.saveNote {
                    presentation.wrappedValue.dismiss()
                }
            }) {
                Image(systemName: "checkmark")
            }
        }).padding().background(backgroundColor)
            .onAppear{
                viewModel.setParamsAndLoadNotes(noteDataSource: noteDataSource, noteId: noteId)
            }
    }
}

struct NoteDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        EmptyView()
    }
}
