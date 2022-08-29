package com.example.noteapp.presentation.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.NoteItemBinding
import com.example.noteapp.domain.model.Colors
import com.example.noteapp.domain.model.Note
import java.text.DateFormat
import java.text.SimpleDateFormat

class NotesListAdapter: ListAdapter<Note, NotesListAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var listener: NoteActionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun attachListenerAdapter(listener: NoteActionListener){
        this.listener = listener
    }


    inner class ViewHolder(
        private val binding: NoteItemBinding,
        private val listener: NoteActionListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Note){
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

            binding.tvNoteTitle.text = item.title
            binding.tvNoteContent.text = item.content
            binding.tvNoteDate.text = simpleDateFormat.format(item.timestamp)


            binding.cvNote.setOnClickListener{ item.id?.let { it1 -> listener.noteEditClick(it1) } }

            binding.ivTrash.setOnClickListener{ item.id?.let { it1 -> listener.noteDeleteClick(it1) } }

            when(item.color){
                 Colors.BLUE-> {
                     binding.clLayoutCard.background = getDrawable(binding.root.context, R.color.card_blue)
                 }
                Colors.YELLOW-> {
                    binding.clLayoutCard.background = getDrawable(binding.root.context, R.color.card_yellow)
                }
                Colors.ORANGE-> {
                    binding.clLayoutCard.background = getDrawable(binding.root.context, R.color.card_orange)
                }
                Colors.RED-> {
                    binding.clLayoutCard.background = getDrawable(binding.root.context, R.color.red_primary)
                }
            }
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
}