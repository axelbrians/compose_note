package com.machina.compose_notes.ui.view.home

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machina.compose_notes.data.model.Note
import com.machina.compose_notes.data.repository.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepositoryImpl
): ViewModel() {

    val allNotes = repository.getAllNotes()

    suspend fun addNewNote(note: Note) {
        repository.addNewNote(note)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.changeNoteVisibility(note.copy(isVisible = false))
        }
    }

    fun undoDeleteNote(note: Note) {
        viewModelScope.launch {
            repository.changeNoteVisibility(note.copy(isVisible = true))
        }
    }

}
