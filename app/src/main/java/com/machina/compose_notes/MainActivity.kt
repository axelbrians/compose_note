package com.machina.compose_notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machina.compose_notes.nav.MainRoutes
import com.machina.compose_notes.view.add_note.AddNoteScreen
import com.machina.compose_notes.view.home.HomeScreen
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen") {
        composable(MainRoutes.HOME_SCREEN) {
            HomeScreen(
                navController = navController,
                modifier = Modifier
            )
        }
        composable(MainRoutes.ADD_NOTE_SCREEN) {
            AddNoteScreen(navController = navController)
        }
    }
}






@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}

