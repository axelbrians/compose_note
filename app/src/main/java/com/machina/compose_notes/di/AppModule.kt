package com.machina.compose_notes.di

import android.content.Context
import androidx.room.Room
import com.machina.compose_notes.data.local.ComposeNoteDatabase
import com.machina.compose_notes.data.local.NoteDatabaseDao
import com.machina.compose_notes.data.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabaseDao(
        composeNoteDatabase: ComposeNoteDatabase
    ): NoteDatabaseDao {
        return composeNoteDatabase.noteDao()
    }

    @Singleton
    @Provides
    fun provideComposeNoteDatabase(
        @ApplicationContext appContext: Context
    ): ComposeNoteDatabase {
        return Room.databaseBuilder(
                appContext,
                ComposeNoteDatabase::class.java,
                "compose_note_database"
            ).fallbackToDestructiveMigration()
             .build()
    }

}