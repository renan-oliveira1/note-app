package com.example.noteapp.presentation.notes.adapter

import com.example.noteapp.domain.model.Note

interface NoteActionListener {
    fun noteEditClick(id: Int)
    fun noteDeleteClick(id: Int)
}