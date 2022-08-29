package com.example.noteapp.domain.use_case

import com.example.noteapp.domain.model.InvalidNoteException
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {

        return repository.getNoteById(id)
    }
}