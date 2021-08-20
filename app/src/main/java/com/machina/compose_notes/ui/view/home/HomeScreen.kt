package com.machina.compose_notes.ui.view.home

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.machina.compose_notes.data.model.Note
import com.machina.compose_notes.nav.MainRoutes
import com.machina.compose_notes.ui.view.composable.ItemNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val notes by remember(viewModel) {
        viewModel.allNotes
    }.collectAsState(listOf())

    var isDialogVisible by remember { mutableStateOf(false) }
    val snackHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()

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
        scaffoldState = rememberScaffoldState(snackbarHostState = snackHostState),
        backgroundColor = MaterialTheme.colors.background
    ) {

        LazyColumn(state = lazyListState) {
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
                        viewModel.deleteNote(it)
                        coroutineScope.launch {
                            snackHostState.currentSnackbarData?.dismiss()
                            val result = snackHostState.showSnackbar(
                                message = "Note ${item.title} deleted",
                                actionLabel = "Undo"
                            )

                            when (result) {
                                SnackbarResult.Dismissed -> {  }
                                SnackbarResult.ActionPerformed -> {
                                    viewModel.undoDeleteNote(it)
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
                            val firstKey = lazyListState.layoutInfo.visibleItemsInfo.first().key
                            val lastKey = lazyListState.layoutInfo.visibleItemsInfo.last().key
                            Timber.d("key $lastKey")
                            if (lastKey is Int && firstKey is Int) {
                                for (index in (lastKey + 1) until notes.size) {
                                    viewModel.deleteNote(notes[index])
                                }

                                for (index in 0 until firstKey - 1) {
                                    viewModel.deleteNote(notes[index])
                                }

                                for (index in (firstKey - 1) until lastKey) {
                                    viewModel.deleteNote(notes[index])
                                    delay(150)
                                }
                            } else {
                                for (index in (notes.size - 1) downTo 0) {
                                    val note = notes[index]
                                    if (note.id < 0) continue
                                    viewModel.deleteNote(note)
                                }
                            }
                            snackHostState.currentSnackbarData?.dismiss()
                            val result = snackHostState.showSnackbar(
                                message = "All note deleted",
                                actionLabel = "Undo"
                            )

                            when (result) {
                                SnackbarResult.Dismissed -> {  }
                                SnackbarResult.ActionPerformed -> {
                                    for (index in (notes.size - 1) downTo 0) {
                                        with(notes[index]) {
                                            if (id >= 0) {
                                                viewModel.undoDeleteNote(this)
                                                delay(150)
                                            }
                                        }
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

//            IconButton(onClick = {
//                    ioCoroutine.launch {
//                        Timber.d("add dummy note")
//                        viewModel.addNewNote(
//                            Note(
//                                "Empty title",
//                                "lorem ipsum dolor sit amet",
//                                "",
//                                true
//                            )
//                        )
//                    }
//                }
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "Add")
//            }
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