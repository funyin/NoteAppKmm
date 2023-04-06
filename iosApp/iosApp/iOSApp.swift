import SwiftUI
import shared
@main
struct iOSApp: App {
    private let datbaseModule = DatabaseModule()
	var body: some Scene {
		WindowGroup {
            NavigationView{
                NoteListScreen(dataSourcse: datbaseModule.noteDataSource)
            }.accentColor(.black)
		}
	}
}
