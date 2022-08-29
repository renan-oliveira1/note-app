package com.example.noteapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isEmpty
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityAddNoteBinding
import com.example.noteapp.domain.model.Colors
import com.example.noteapp.domain.model.Note
import com.example.noteapp.presentation.add_edit_note.AddEditNoteView
import com.example.noteapp.presentation.notes.NotesViewModel
import com.example.noteapp.presentation.notes.adapter.ConstantExtra.EXTRA_NOTE_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class AddNoteActivity: AppCompatActivity() {

    private val binding by lazy{ActivityAddNoteBinding.inflate(layoutInflater)}
    private var idNoteEdit: Int? = null
    private val viewModel by viewModels<AddEditNoteView>()

    private var colorSelected: Colors = Colors.BLUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener{
            finish()
        }
        verifyExtraContent()
        saveNoteButton()
        noteColorInput()
    }

    private fun verifyExtraContent(){
        val noteId: Int? = intent.extras?.get(EXTRA_NOTE_ID) as Int?
        if(noteId != null) {
            idNoteEdit = noteId
            observerEditNote(noteId)
        }
    }

    private fun saveNoteButton(){
        binding.fabAddNote.setOnClickListener {
            val title = binding.tilTitle.editText?.text.toString()
            val content = binding.tilContent.editText?.text.toString()
            if(title.isEmpty() || content.isEmpty()){
                Toast.makeText(this, "Preencha os campos acima!", Toast.LENGTH_SHORT).show()
            }else{
                val note = Note(
                    id = idNoteEdit,
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis(),
                    color = colorSelected
                )
                viewModel.save(note)
                finish()
            }
        }
    }

    private fun observerEditNote(id: Int){
        viewModel.getNote(id)
        viewModel.editNote.observe(this){ note ->
            binding.tilTitle.editText?.setText(note.title)
            binding.tilContent.editText?.setText(note.content)
        }
    }

    private fun noteColorInput(){
        binding.tvNoteSelectColorBlue.setOnClickListener {
            binding.clAddEditNote.background = getDrawable(R.color.card_blue)
            colorSelected = Colors.BLUE
        }
        binding.tvNoteSelectColorYellow.setOnClickListener {
            binding.clAddEditNote.background = getDrawable(R.color.card_yellow)
            colorSelected = Colors.YELLOW
        }
        binding.tvNoteSelectColorOrange.setOnClickListener {
            binding.clAddEditNote.background = getDrawable(R.color.card_orange)
            colorSelected = Colors.ORANGE
        }
        binding.tvNoteSelectColorRed.setOnClickListener {
            binding.clAddEditNote.background = getDrawable(R.color.red_primary)
            colorSelected = Colors.RED
        }
    }


}