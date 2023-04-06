package com.initbase.noteappkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.initbase.noteappkmm.android.note_detail.NoteDetailScreen
import com.initbase.noteappkmm.android.note_list.NoteListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                ApplicationView()
            }
        }
    }
}

@Composable
private fun ApplicationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "note_list") {
        composable("note_list") {
            NoteListScreen(navController = navController)
        }
        composable("note_detail/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    defaultValue = -1L
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
            NoteDetailScreen(noteId = noteId, navController = navController)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ApplicationView()
    }
}
