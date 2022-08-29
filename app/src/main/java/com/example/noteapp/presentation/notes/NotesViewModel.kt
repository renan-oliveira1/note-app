package com.example.noteapp.presentation.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.use_case.GetNotesUseCase
import com.example.noteapp.domain.use_case.NotesUseCases
import com.example.noteapp.domain.util.NoteOrder
import com.example.noteapp.domain.util.OrderType
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
private val notesUseCase: NotesUseCases
): ViewModel(){

    private var _state = MutableLiveData<NotesState>()
    var state: LiveData<NotesState> = _state

    private var recentlyDeleteNote: Note? = null

    var orderType: OrderType = OrderType.Ascending
    private var noteOrder: NoteOrder = NoteOrder.Date(orderType)


    fun getNotes() {
        viewModelScope.launch {
            val notesList = notesUseCase.getNotesUseCase(noteOrder).first()
            val notesState = NotesState(
                notesList,
                noteOrder
            )
            _state.postValue(notesState)
        }

    }

    fun setNoteOrderTypeFilter(orderType: OrderType){
        noteOrder.orderType = orderType
        getNotes()
    }

    fun setNoteOrderFilter(order: NoteOrder){
        noteOrder = order
        getNotes()
    }


    fun deleteNote(id: Int){
        viewModelScope.launch{
            val note =  notesUseCase.getNoteUseCase(id)
            if(note != null) {
                notesUseCase.deleteNoteUseCase.invoke(note)
                recentlyDeleteNote = note
            }
            getNotes()
        }
    }

    fun restoreNote(){
        viewModelScope.launch {
            notesUseCase.addNoteUseCase(recentlyDeleteNote ?: return@launch)
            recentlyDeleteNote = null
            getNotes()
        }
    }


}