package com.machina.compose_notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machina.compose_notes.nav.MainRoutes
import com.machina.compose_notes.ui.theme.ComposeNoteTheme
import com.machina.compose_notes.ui.view.add_note.AddNoteScreen
import com.machina.compose_notes.ui.view.home.HomeScreen
import com.machina.compose_notes.ui.view.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNoteTheme {
                MainContent()
            }
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

            val modalBottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden
            )
            val scope = rememberCoroutineScope()
            ModalBottomSheetLayout(
                sheetShape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
                sheetState = modalBottomSheetState,
                sheetContent = {
                    Column(modifier = Modifier.padding(vertical = 18.dp)) {
                        Surface(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch { modalBottomSheetState.hide() }
                            }
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                        ) {
                            Text(text = "Sort By")
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            ) {
                HomeScreen(
                    bottomSheetState = modalBottomSheetState,
                    navController = navController,
                    modifier = Modifier
                )
            }



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

