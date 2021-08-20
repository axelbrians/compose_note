package com.machina.compose_notes.data.repository

import com.machina.compose_notes.data.local.NoteDatabaseDao
import com.machina.compose_notes.data.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NoteRepository {
    suspend fun getAllNotes(): Flow<List<Note>>


}