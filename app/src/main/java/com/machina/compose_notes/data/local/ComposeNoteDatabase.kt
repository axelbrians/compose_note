package com.machina.compose_notes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.machina.compose_notes.data.model.Note
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

private const val DB_VERSION = 1

@Database(entities = [Note::class], version = DB_VERSION)
abstract class ComposeNoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDatabaseDao

    companion object {
        private var INSTANCE: ComposeNoteDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): ComposeNoteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ComposeNoteDatabase::class.java,
                        "compose_note_database"
                    ).fallbackToDestructiveMigration()
                     .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}