package com.example.noteapp.presentation.notes

import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.util.NoteOrder

sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
