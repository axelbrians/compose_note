package com.machina.compose_notes.view.home

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.machina.compose_notes.data.model.Note
import com.machina.compose_notes.data.source.LocalSource
import com.machina.compose_notes.nav.MainRoutes
import com.machina.compose_notes.view.composable.ItemNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var notes by remember { mutableStateOf(LocalSource.getDummyNotes()) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "Compose Note",
                onActionClick = { isDialogVisible = !isDialogVisible }
            )
        },
        bottomBar = { MainBottomBar() },
        floatingActionButton = { MainFab(navController = navController) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        backgroundColor = MaterialTheme.colors.background
    ) {
        LazyColumn(state = listState) {
            items(
                items = notes,
                key = { item: Note -> item.id }
            ) { item ->
                AnimatedVisibility(
                    visible = item.isVisible,
                    enter = fadeIn(
                        animationSpec = TweenSpec(200, 0, FastOutLinearInEasing)
                    ),
                    exit = fadeOut(
                        animationSpec = TweenSpec(200, 200, FastOutLinearInEasing)
                    )
                ) {
                    ItemNote(item) {
                        notes = changeNoteVisibility(notes, it)
                        coroutineScope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val result = snackbarHostState.showSnackbar(
                                message = "Note ${item.title} deleted",
                                actionLabel = "Undo"
                            )

                            when (result) {
                                SnackbarResult.Dismissed -> {  }
                                SnackbarResult.ActionPerformed -> {
                                    notes = changeNoteVisibility(notes, it)
                                }
                            }
                        }
                    }
                }
            }
        }


        if (isDialogVisible) {
            AlertDialog(
                title = { Text(text = "Delete all notes?") },
                onDismissRequest = { isDialogVisible = !isDialogVisible },
                confirmButton = {
                    TextButton(onClick = {
                        coroutineScope.launch {
                            val iterate = notes
                            val firstKey = listState.layoutInfo.visibleItemsInfo.first().key
                            val lastKey = listState.layoutInfo.visibleItemsInfo.last().key
                            Log.d("Home", "key $lastKey")
                            if (lastKey is Int && firstKey is Int) {
                                for (index in (lastKey + 1) until iterate.size) {
                                    notes = changeNoteVisibility(notes, iterate[index])
                                }

                                for (index in 0 until firstKey - 1) {
                                    notes = changeNoteVisibility(notes, iterate[index])
                                }

                                for (index in (firstKey - 1)..lastKey) {
                                    notes = changeNoteVisibility(notes, iterate[index])
                                    delay(150)
                                }
                            } else {
                                for (index in (iterate.size - 1) downTo 0) {
                                    val note = iterate[index]
                                    if (note.id < 0) continue
                                    notes = changeNoteVisibility(notes, iterate[index])
                                }
                            }
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val result = snackbarHostState.showSnackbar(
                                message = "All note deleted",
                                actionLabel = "Undo"
                            )

                            when (result) {
                                SnackbarResult.Dismissed -> {  }
                                SnackbarResult.ActionPerformed -> {
                                    for (index in (iterate.size - 1) downTo 0) {
//                                        Log.d("Home", "index $index")
                                        if (iterate[index].id < 0) continue
                                        notes = changeNoteVisibility(notes, iterate[index])
//                                        delay(150)
                                    }
                                }
                            }
                        }
                        isDialogVisible = !isDialogVisible
                    }) { Text(text = "Delete all") }
                },
                dismissButton = {
                    TextButton(onClick = { isDialogVisible = !isDialogVisible }) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    }
}

fun changeNoteVisibility(notes: List<Note>, removedNote: Note): List<Note> {
    return notes.map { currentNote ->
        if (currentNote.id == removedNote.id) {
            val visibility = currentNote.isVisible
            currentNote.copy(isVisible = !visibility)
        } else {
            currentNote
        }
    }
}

@Composable
fun MainTopAppBar(
    title: String,
    onActionClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    )
}

@Composable
fun MainBottomBar() {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    ) { }
}

@Composable
fun MainFab(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate(MainRoutes.ADD_NOTE_SCREEN) },
        content = { Icon(Icons.Default.Add, contentDescription = "Add New Note") }
    )
}