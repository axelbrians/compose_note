package com.machina.compose_notes.ui.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.machina.compose_notes.nav.MainRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun MainBottomBar(
    bottomSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { coroutineScope.launch { bottomSheetState.show() } }) {
                Icon(Icons.Default.Menu, contentDescription = "Choose List")
            }

            IconButton(onClick = { Timber.d("Left button") }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }
        }
    }
}

@Composable
fun MainFab(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate(MainRoutes.ADD_NOTE_SCREEN) },
        content = { Icon(Icons.Default.Add, contentDescription = "Add New Note") }
    )
}