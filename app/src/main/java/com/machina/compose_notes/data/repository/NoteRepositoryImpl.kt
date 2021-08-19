package com.machina.compose_notes.data.repository

import com.machina.compose_notes.data.local.NoteDatabaseDao
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDatabaseDao: NoteDatabaseDao
) {


}