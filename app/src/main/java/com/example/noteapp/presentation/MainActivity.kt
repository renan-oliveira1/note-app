package com.example.noteapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.domain.model.Note
import com.example.noteapp.domain.util.NoteOrder
import com.example.noteapp.domain.util.OrderType
import com.example.noteapp.presentation.notes.NotesViewModel
import com.example.noteapp.presentation.notes.adapter.ConstantExtra
import com.example.noteapp.presentation.notes.adapter.NoteActionListener
import com.example.noteapp.presentation.notes.adapter.NotesListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy{ NotesListAdapter() }
    private val viewModel by viewModels<NotesViewModel>()
    private lateinit var noteItemListener: NoteActionListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvNotes.adapter = adapter

        getNotesList()
        dealFilterMenu()
        addNoteButton()
        setNoteAdapterListener()
        setObserverNoteFilterRadio()

    }

    override fun onResume() {
        super.onResume()
        adapter.attachListenerAdapter(noteItemListener)
        viewModel.getNotes()
    }


    private fun dealFilterMenu(){
        binding.ivSort.setOnClickListener{
            if(binding.clLayoutFilter.visibility == View.GONE) binding.clLayoutFilter.visibility = View.VISIBLE
            else binding.clLayoutFilter.visibility = View.GONE
        }
    }

    private fun addNoteButton(){
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getNotesList(){
        viewModel.state.observe(this){
            adapter.submitList(it.notes)
        }
    }

    private fun setNoteAdapterListener(){
        noteItemListener = object: NoteActionListener{
            override fun noteEditClick(id: Int) {
                val context = applicationContext
                val intent = Intent(context, AddNoteActivity::class.java).apply {
                    putExtra(ConstantExtra.EXTRA_NOTE_ID, id)
                }
                startActivity(intent)

            }

            override fun noteDeleteClick(id: Int) {
                viewModel.deleteNote(id)
                Snackbar.make(binding.root, "Nota excluida!!", Snackbar.LENGTH_LONG)
                .setAction("Desfazer"){
                    viewModel.restoreNote()
                }.show()
            }
        }
    }

    private fun setObserverNoteFilterRadio() {
        binding.rgOrder.setOnCheckedChangeListener { _, i ->
            when (i) {
                binding.rbtnTitle.id -> {
                    viewModel.setNoteOrderFilter(
                        NoteOrder.Title(viewModel.orderType)
                    )
                }
                binding.rbtnDate.id -> {
                    viewModel.setNoteOrderFilter(
                        NoteOrder.Date(viewModel.orderType)
                    )
                }
                binding.rbtnColor.id -> {
                    viewModel.setNoteOrderFilter(
                        NoteOrder.Color(viewModel.orderType)
                    )
                }
            }
        }

        binding.rgOrderType.setOnCheckedChangeListener{ _, i ->
            when(i){
                binding.rbtnAscending.id -> {
                    viewModel.setNoteOrderTypeFilter(OrderType.Ascending)
                }
                binding.rbtnDescending.id -> {
                    viewModel.setNoteOrderTypeFilter(OrderType.Descending)
                }
            }
        }
    }

}