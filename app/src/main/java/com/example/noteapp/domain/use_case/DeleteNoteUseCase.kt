package com.example.noteapp.domain.use_case

import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }

}