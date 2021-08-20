package com.machina.compose_notes.data.local

import androidx.room.*
import com.machina.compose_notes.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from notes_list")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewNote(note: Note)

    @Update
    suspend fun changeNoteVisibility(note: Note)
}