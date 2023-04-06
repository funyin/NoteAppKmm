//
//  NoteItem.swift
//  iosApp
//
//  Created by Funyinoluwa Kashimawo on 02/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {
    var note: Note
    var onDeleteClick: ()->Void
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick){
                    Image(systemName: "xmark")
                }.foregroundColor(.black)
            }.padding(.bottom,3)
            
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom,3)
            HStack{
                Spacer()
                Text(DateTimeUtilKt.dateTimeUTil.formatNoteDate(dateTime: note.created, format: "yyyy-MM-dd HH:mm"))
                    .font(.footnote)
                    .fontWeight(.semibold)
            }
            
        }.padding()
            .background(Color(hex: note.colorHex))
            .clipShape(RoundedRectangle(cornerRadius:5.0))
    }
}

struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note: Note(id: nil, title: "My note", content: "Note Content", colorHex: Note.companion.generateRandomColor(), created: DateTimeUtilImpl().now())) {
                
            }
    }
}
