package com.machina.compose_notes.data.repository

import com.machina.compose_notes.data.local.NoteDatabaseDao
import com.machina.compose_notes.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDatabaseDao: NoteDatabaseDao
) {

    fun getAllNotes(): Flow<List<Note>> {
        return noteDatabaseDao.getAllNotes().distinctUntilChanged()
    }

   suspend fun addNewNote(note: Note) {
       noteDatabaseDao.addNewNote(note)
   }

    suspend fun changeNoteVisibility(note: Note) {
        noteDatabaseDao.changeNoteVisibility(note)
    }


    private fun<T> isEqual(first: List<T>, second: List<T>): Boolean {

        if (first.size != second.size) {
            return false
        }

        first.forEachIndexed { index, value -> if (second[index] != value) { return false} }
        return true
    }
}