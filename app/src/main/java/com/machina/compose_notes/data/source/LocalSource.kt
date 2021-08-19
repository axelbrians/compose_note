package com.machina.compose_notes.data.source

import com.machina.compose_notes.data.model.Note

object LocalSource {

    fun getDummyNotes() =
    listOf(
        Note(1, "1st Note", "Curabitur gravida eros sed magna."),
        Note(2, "2nd Note", "Etiam posuere volutpat luctus. Sed."),
        Note(3, "3rd Note", "Lorem ipsum dolor sit amet"),
        Note(4, "4th Note", "Curabitur gravida eros sed magna."),
        Note(5, "5th Note", "Etiam posuere volutpat luctus. Sed."),
        Note(6, "6th Note", "Lorem ipsum dolor sit amet"),
        Note(7, "7th Note", "Curabitur gravida eros sed magna."),
        Note(8, "8th Note", "Etiam posuere volutpat luctus. Sed."),
        Note(9, "9th Note", "Lorem ipsum dolor sit amet"),
        Note(10, "10th Note", "Curabitur gravida eros sed magna."),
        Note(11, "11th Note", "Etiam posuere volutpat luctus. Sed."),
        Note(12, "12th Note", "Lorem ipsum dolor sit amet"),
        Note(13, "13th Note", "Curabitur gravida eros sed magna."),
        Note(14, "14th Note", "Etiam posuere volutpat luctus. Sed."),
        Note(15, "15th Note", "Lorem ipsum dolor sit amet"),
        Note(16, "16th Note", "Curabitur gravida eros sed magna."),
        Note(17, "17th Note", "Etiam posuere volutpat luctus. Sed."),
        Note(18, "18th Note", "Lorem ipsum dolor sit amet"),
        Note(-1, "", "")
    )
}