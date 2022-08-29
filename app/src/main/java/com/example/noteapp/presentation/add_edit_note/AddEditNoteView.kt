package com.example.noteapp.presentation.add_edit_note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.use_case.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteView @Inject constructor(
    private val notesUseCases: NotesUseCases
): ViewModel(){

    private var _editNote = MutableLiveData<Note>()
    var editNote: LiveData<Note> = _editNote

    fun save(note: Note){
        viewModelScope.launch {
            notesUseCases.addNoteUseCase.invoke(note)
        }
    }

    fun getNote(id: Int){
        viewModelScope.launch {
            _editNote.postValue(notesUseCases.getNoteUseCase(id))
        }
    }



}