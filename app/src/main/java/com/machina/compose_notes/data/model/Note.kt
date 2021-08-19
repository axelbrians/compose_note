package com.machina.compose_notes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_list")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "note_title")
    var title: String = "",

    @ColumnInfo(name = "note_content")
    var content: String = "",

    @ColumnInfo(name = "note_updated_at")
    var updatedAt: String = "",

    @ColumnInfo(name = "note_isvisible")
    var isVisible: Boolean = true
)
