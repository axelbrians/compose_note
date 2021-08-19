package com.machina.compose_notes.view.add_note

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddNoteScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var noteTitle by remember {
            mutableStateOf("")
        }
        var noteContent by remember {
            mutableStateOf("")
        }
        TextField(
            value = noteTitle,
            onValueChange = { noteTitle = it },
            label = { Text(text = "Title") },
            maxLines = 3,
            modifier = modifier.fillMaxWidth().padding(
                horizontal = 18.dp,
                vertical = 8.dp
            )
        )
        Spacer(modifier = modifier.height(18.dp))
        TextField(
            value = noteContent,
            onValueChange = { noteContent = it },
            label = { Text(text = "Content") },
            maxLines = 20,
            modifier = modifier.fillMaxWidth().padding(
                horizontal = 18.dp,
                vertical = 8.dp
            )
        )
    }
}

@Composable
fun AddNoteTopAppBar(
    navController: NavController,
    title: String,
    onActionClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

