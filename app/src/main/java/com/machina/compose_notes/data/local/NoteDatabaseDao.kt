package com.machina.compose_notes.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.machina.compose_notes.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from notes_list")
    suspend fun getAllNotes(): Flow<List<Note>>

    @Update
    suspend fun changeNoteVisibility(note: Note, isVisible: Boolean)
}